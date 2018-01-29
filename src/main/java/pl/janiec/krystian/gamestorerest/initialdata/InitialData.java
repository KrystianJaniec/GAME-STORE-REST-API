package pl.janiec.krystian.gamestorerest.initialdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.janiec.krystian.gamestorerest.domain.Category;
import pl.janiec.krystian.gamestorerest.domain.Customer;
import pl.janiec.krystian.gamestorerest.domain.Producer;
import pl.janiec.krystian.gamestorerest.domain.Product;
import pl.janiec.krystian.gamestorerest.repository.CategoryRepository;
import pl.janiec.krystian.gamestorerest.repository.CustomerRepository;
import pl.janiec.krystian.gamestorerest.repository.ProducerRepository;
import pl.janiec.krystian.gamestorerest.repository.ProductRepository;

@Component
public class InitialData implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private ProducerRepository producerRepository;
    private ProductRepository productRepository;

    @Autowired
    public InitialData(CategoryRepository categoryRepository, CustomerRepository customerRepository, ProducerRepository producerRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.producerRepository = producerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        loadCategoryList();
        loadCustomerList();
        loadProducerList();
        loadProductList();
    }

    private void loadCategoryList() {
        Category shooter = new Category();
        shooter.setName("shooter");

        Category action = new Category();
        action.setName("action");

        Category adventure = new Category();
        adventure.setName("adventure");

        Category rolePlaying = new Category();
        rolePlaying.setName("role-playing");

        Category simulation = new Category();
        simulation.setName("simulation");

        Category strategy = new Category();
        strategy.setName("strategy");

        Category sports = new Category();
        sports.setName("sports");

        Category mmo = new Category();
        sports.setName("mmo");

        categoryRepository.save(shooter);
        categoryRepository.save(action);
        categoryRepository.save(adventure);
        categoryRepository.save(rolePlaying);
        categoryRepository.save(simulation);
        categoryRepository.save(strategy);
        categoryRepository.save(sports);
        categoryRepository.save(mmo);
    }

    private void loadCustomerList() {
        Customer man = new Customer();
        man.setFirstName("Adam");
        man.setLastName("Kowalski");
        man.setId(1L);

        Customer woman = new Customer();
        woman.setFirstName("Anna");
        woman.setLastName("Nowak");
        woman.setId(2L);

        customerRepository.save(man);
        customerRepository.save(woman);
    }

    private void loadProducerList() {
        Producer smallCompany = new Producer();
        smallCompany.setCompanyName("CD Projekt Red");
        smallCompany.setCompanyShortcutName("CDPR");

        Producer averageCompany = new Producer();
        averageCompany.setCompanyName("2K Games Company");
        averageCompany.setCompanyShortcutName("2K");

        Producer bigCompany = new Producer();
        bigCompany.setCompanyName("Electronic Arts");
        bigCompany.setCompanyShortcutName("EA");

        producerRepository.save(smallCompany);
        producerRepository.save(bigCompany);
    }

    private void loadProductList() {
        Product witcher = new Product();
        witcher.setProductName("The Witcher 3: Wild Hunt");
        witcher.setProductDescription("The Witcher 3: Wild Hunt is a 2015 action role-playing video game developed and published by CD Projekt");
        witcher.setProductPrice(99.99);
        witcher.setProducerId(1);
        witcher.setCategoryName("action");

        Product fifa = new Product();
        fifa.setProductName("FIFA 18");
        fifa.setProductDescription("Score incredible goals in FIFA 18 as new movement and finishing animations unlock more fluid striking and heading of the ball");
        fifa.setProductPrice(189.99);
        fifa.setProducerId(3);
        fifa.setCategoryName("sports");

        Product mafia = new Product();
        mafia.setProductName("Mafia II");
        mafia.setProductDescription("Mafia II is an open world action-adventure video game developed by 2K Czech and published by 2K Games");
        mafia.setProductPrice(128.90);
        mafia.setProducerId(2);
        mafia.setCategoryName("shooter");

        productRepository.save(mafia);
        productRepository.save(witcher);
        productRepository.save(fifa);
    }
}
