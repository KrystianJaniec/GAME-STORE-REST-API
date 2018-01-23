package pl.janiec.krystian.gamestorerest.initialdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.janiec.krystian.gamestorerest.domain.Category;
import pl.janiec.krystian.gamestorerest.domain.Customer;
import pl.janiec.krystian.gamestorerest.domain.Producer;
import pl.janiec.krystian.gamestorerest.repository.CategoryRepository;
import pl.janiec.krystian.gamestorerest.repository.CustomerRepository;

@Component
public class InitialData implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public InitialData(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        loadCategoryList();
        loadCustomerList();
        loadProducerList();
    }

    private void loadCategoryList() {
        Category shooter = new Category();
        shooter.setName("Shooter");

        Category action = new Category();
        action.setName("Action");

        Category adventure = new Category();
        adventure.setName("Adventure");

        Category rolePlaying = new Category();
        rolePlaying.setName("Role-playing");

        Category simulation = new Category();
        simulation.setName("Simulation");

        Category strategy = new Category();
        strategy.setName("Strategy");

        Category sports = new Category();
        sports.setName("Sports");

        Category mmo = new Category();
        sports.setName("MMO");

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
    }
}
