Voici la Class Shop
-------------------
* Dans Model
************
@Entity
@Table(name = "shop")
public class Shop {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ownerName;
    private long shopNo;
    private String shopType;
    private String address;
    private String country;
}
------------------------------------------------------------------------
AFFICHER LES DONNEES DONT LA COLONNE "String ownerName = Davide Becker"
------------------------------------------------------------------------

* Dans Repository
*****************
@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer>{

      @Query(value = "select * from shop s where s.owner_name like %:keyword%", nativeQuery = true)
      List<Shop> findByKeyword(@Param("keyword") String keyword);
}

* Dans Service
***************
@Service
public class ShopService {
 
    @Autowired
    private ShopRepository repository;
 
    public List<Shop> getAllShops(){
        List<Shop> list =  (List<Shop>)repository.findAll();
        return list;
    }
 
     public List<Shop> getByKeyword(String keyword){
         return repository.findByKeyword(keyword);
     }
}

* Dans Controller
******************
@Controller
public class ShopController {
   
    @Autowired
    private ShopService service;
 
    @RequestMapping("/")
    public String home(Shop shop, Model model, String keyword) {
      
        keyword = "Davide Bishop";
        List<Shop> list = service.getByKeyword(keyword);
        model.addAttribute("list", list);
		 
       return "index";
    }
}

* Dans Thymeleaf
*****************

 <table class="table table-bordered table-sm mt-2">
      <thead>
	  <tr>
	      <th>Id</th>
	      <th>Owner Name</th>
	      <th>Shop Type</th>
	      <th>Shop Number</th>
	      <th>Address</th>
	      <th>Country</th>
	      <th>Action</th>
	 </tr>
     </thead>
     <tbody>
	 <tr th:each="l : ${list}" th:field="${l}">
	      <td th:text="${lStat.index+1}"></td>
	      <td th:text="${l.ownerName}"></td>
	      <td th:text="${l.shopType}"></td>
	      <td th:text="${l.shopNo}"></td>
	      <td th:text="${l.address}"></td>
	      <td th:text="${l.country}"></td>
	      <td>
		   <a th:href="@{/update/{id}(id=${l.id})}" ><i class="fa fa-edit" ></i></a>
		   <a th:href="@{/delete/{id}(id=${l.id})}" ><i class="fa fa-remove"></i></a>
	      </td>
	 </tr>
    </tbody>
 </table>
 
 
 
          ETAPE II
        **********
RECHERCHE TOTALE DANS TOUTE LA BASE DE DONNEES. CECI CONCERNE TOUTS LES CHAMPS POSSIBLES POUR LA RECHERCHE 

SUPPOSONS JE VEUX CHERCHER UN ENREGISTREMENT DANS TOUTES LES DONNEES DE LA CLASSE Visitor

* Dans Model
************
@Entity
public class Visitor {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "typelogement")
	private String typelogement;
	
	@Column(name = "secteur")
	private String secteur;
	
	@Column(name = "titre")
	private String titre;	
	
	@Column(name = "prix")
        private String prix;
	
}

* Dans Repository
*****************
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>{

    @Query("SELECT v FROM Visitor v WHERE CONCAT(v.typelogement, ' ', v.secteur, ' ', v.titre, ' ', v.prix) LIKE %?1%")
    public List<Visitor> searchword(String search);
	
}

* Dans Service
***************
@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    public List<Visitor> getAllVisitorsByDesc(String search){ 
	if(search != null) {
	   return visitorRepository.searchword(search);
	}
	return visitorRepository.findAll(Sort.by("id").descending());
    }
	
    public List<Visitor> getAllVisitors() {
	return visitorRepository.findAll();
    }
}

* Dans Controller
*****************

@Controller
public class VisitorController {
	
	@Autowired
	private VisitorService visitorService;

        @GetMapping("/logements")
	String showListOfVisitors(Model model, @Param("search") String search) {
		List<Visitor> visitor = visitorService.getAllVisitorsByDesc(search);
		model.addAttribute("visitor", visitor);
		model.addAttribute("search", search);
		return "logement";
	}
}

* Dans Thymeleaf
****************

<form role="search" method="get" id="searchform"  >
     <div class="input-group">
	  <input type="text" class="form-control" name="search" id="search" th:value="${search}" size="50"> 
	  <div class="input-group-btn">
		<button class="btn btn-default"  id="searchsubmit"  type="submit">
		<strong style="color:blue;">Search</strong></button>                           
	  </div>
     </div> 
</form> 



