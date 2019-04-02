package laborator2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import domain.Student;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import view.UI;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private StudentXMLRepo studentRepository;
    private Service service;

    @Before
    public void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        this.studentRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addStudent(){
        String filenameStudent = "fisiere/Studenti.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        Student student = new Student("32","Mada",932, "buf@c.com");
        assertTrue(studentXMLRepository.findOne(student.getID()) == null) ;
        studentXMLRepository.save(student);
        assertTrue(studentXMLRepository.findOne(student.getID()) != null);
        studentXMLRepository.delete("32");
    }

    @Test
    public void addStudent2(){
        String filenameStudent = "fisiere/Studenti.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        int size = 0;
        for ( Student s : studentXMLRepository.findAll()){
            size++;
        }
        Student student1 = new Student("33","M",930, "bu@c.com");
        studentXMLRepository.save(student1);
        int size2 = 0 ;
        for ( Student s : studentXMLRepository.findAll()){
            size2++;
        }
        assertTrue( size + 1 == size2);
        studentXMLRepository.delete("33");
    }

    @Test
    public void addStudent3() {
        String filenameStudent = "fisiere/Studenti.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        try {
            studentXMLRepository.findOne(null);

        } catch (Exception e) {
            System.out.println("-----");
            assertEquals("ID-ul nu poate fi null!", e.getMessage().trim());

        }
    }

    @Test
    public void addStudent4() {
        //grupa negativa
        Student s = new Student("123", "Ana", -100,"ana@c");

        try {
            Student student = this.service.addStudent(s);
        } catch (Exception e) {
            assertEquals("Grupa incorecta!", e.getMessage().trim());

        }
    }


    @Test
    public void addStudent5() {
        //email null
        Student s = new Student("123", "Ana", 100,"");

        try {
            Student student = this.service.addStudent(s);
        } catch (Exception e) {
            assertEquals("Email incorect!", e.getMessage().trim());

        }
    }

    @Test
    public void addStudent6() {
        //nume null
        Student s = new Student("123", "", 100,"ana@c");

        try {
            Student student = this.service.addStudent(s);
        } catch (Exception e) {
            assertEquals("Nume incorect!", e.getMessage().trim());

        }
    }


    @Test
    public void addStudent7() {
        //id null
        Student s = new Student("", "Ana", 100, "ana@c");
        try {
            Student student = this.service.addStudent(s);
        } catch (Exception e) {
            assertEquals("Id incorect!", e.getMessage().trim());

        }
    }


}
