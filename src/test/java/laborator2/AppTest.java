package laborator2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private StudentXMLRepo studentRepository;
    private TemaXMLRepo temaRepository;
    private Service service;

    @Before
    public void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti.xml";
        String filenameTema = "fisiere/Teme.xml";
        String filenameNota = "fisiere/Note.xml";
        this.studentRepository = new StudentXMLRepo(filenameStudent);
        this.temaRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentRepository, this.temaRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentRepository, studentValidator, this.temaRepository, temaValidator, notaXMLRepository, notaValidator);
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

    @Test
    public void addAssignment() {

        Tema t = new Tema("102","Info", 3,1);
        try {
            this.service.addTema(t);
            Tema t1 = this.service.findTema("102");
            assertTrue(t == t1);
        } catch (Exception e) {
            assertEquals("Numar tema invalid!", e.getMessage().trim());

        }
        this.service.deleteTema("102");
    }

    @Test
    public void addAssignment2() {

        Tema t2 = new Tema("","Info", 3,1);
        try {
            this.service.addTema(t2);
        } catch (Exception e) {
            assertEquals("Numar tema invalid!", e.getMessage().trim());

        }
    }

    @Test
    public void addAssignment3() {
        List<Tema> homeworks = StreamSupport.stream(temaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        int s = homeworks.size();
        assertEquals(homeworks.size(), s);
        temaRepository.save(new Tema("51", "Math", 3, 1));
        homeworks = StreamSupport.stream(temaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(homeworks.size(), s+1);
        this.service.deleteTema("51");
    }

    @Test
    public void addAssignment4() {
        Tema t = temaRepository.save(new Tema(null, "Ana", 3, 2));
        assertEquals(t, null);
    }

    @Test
    public void addAssignment5() {
        try{
             Tema negativeDeadline = this.service.addTema(new Tema("121", "mate", -1, 2));
        }
        catch (Exception e) {
            assertEquals("Deadlineul trebuie sa fie intre 1-14.", e.getMessage().trim());

        }
    }

    @Test
    public void addAssignment6() {
        try {
            Tema t = this.service.addTema(new Tema("53", "", 3, 2));
        }
        catch (Exception e){
            assertEquals("Descriere invalida!", e.getMessage().trim());
        }

    }

    @Test
    public void addAssignment7() {
        try {
            Tema t = this.service.addTema(new Tema("53", "Info", -3, 2));
        }
        catch (Exception e){
            assertEquals("Deadlineul trebuie sa fie intre 1-14.", e.getMessage().trim());
        }

    }


    @Test
    public void addAssignment8() {
        try {
            Tema t = this.service.addTema(new Tema("53", "Info", 16, 2));
        }
        catch (Exception e){
            assertEquals("Deadlineul trebuie sa fie intre 1-14.", e.getMessage().trim());
        }

    }

    @Test
    public void addAssignment9() {
        try {
            Tema t = this.service.addTema(new Tema("53", "Info", 4, -2));
        }
        catch (Exception e){
            assertEquals("Saptamana primirii trebuie sa fie intre 1-14.", e.getMessage().trim());
        }

    }

    @Test
    public void addAssignment10() {
        try {
            Tema t = this.service.addTema(new Tema("53", "Info", 4, 17));
        }
        catch (Exception e){
            assertEquals("Saptamana primirii trebuie sa fie intre 1-14.", e.getMessage().trim());
        }

    }
}

