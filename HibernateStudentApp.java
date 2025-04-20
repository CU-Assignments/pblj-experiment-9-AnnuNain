import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;

@Entity
@Table(name = "students")
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;

    public Student() {}

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}

public class HibernateStudentApp {
    public static void main(String[] args) {
        // Hibernate Configuration
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/studentdb");
        cfg.setProperty("hibernate.connection.username", "root");
        cfg.setProperty("hibernate.connection.password", "your_password");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.addAnnotatedClass(Student.class);

        SessionFactory sessionFactory = cfg.buildSessionFactory();

        // Create
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Student s1 = new Student("Ananya", 21);
        session.save(s1);
        tx.commit();
        session.close();

        // Read
        session = sessionFactory.openSession();
        Student fetched = session.get(Student.class, s1.getId());
        System.out.println("Fetched: " + fetched.getName() + ", Age: " + fetched.getAge());
        session.close();

        // Update
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        fetched.setAge(22);
        session.update(fetched);
        tx.commit();
        session.close();

        // Delete
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        session.delete(fetched);
        tx.commit();
        session.close();

        System.out.println("CRUD operations completed.");
        sessionFactory.close();
    }
}
