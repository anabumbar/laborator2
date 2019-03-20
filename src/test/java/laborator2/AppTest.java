package laborator2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import domain.Student;
import org.junit.Test;
import repository.StudentXMLRepo;
import service.Service;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
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

}
