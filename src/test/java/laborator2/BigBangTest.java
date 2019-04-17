package laborator2;

import domain.Nota;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class BigBangTest {
    private StudentXMLRepo studentRepository;
    private TemaXMLRepo temaRepository;
    private NotaXMLRepo notaRepository;
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
        this.notaRepository = new NotaXMLRepo(filenameNota);
        NotaValidator notaValidator = new NotaValidator(studentRepository, this.temaRepository);

        this.service = new Service(studentRepository, studentValidator, this.temaRepository, temaValidator, this.notaRepository, notaValidator);
    }

    @Test
    public void addAssignment() {
        List<Tema> homeworks = StreamSupport.stream(temaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        int s = homeworks.size();
        assertEquals(homeworks.size(), s);
        temaRepository.save(new Tema("56", "Math", 5, 1));
        homeworks = StreamSupport.stream(temaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(homeworks.size(), s+1);
        this.service.deleteTema("56");
    }

    @Test
    public void addStudent() {
        List<Student> students = StreamSupport.stream(studentRepository.findAll().spliterator(), false).collect(Collectors.toList());
        int s = students.size();
        assertEquals(students.size(), s);
        studentRepository.save(new Student("301","Ana", 923,"ana@c."));
        students = StreamSupport.stream(studentRepository.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(students.size(), s+1);
        this.service.deleteStudent("301");
    }

    @Test
    public void addGrade() {
        List<Nota> grades = StreamSupport.stream(notaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        int s = grades.size();
        assertEquals(grades.size(), s);
        notaRepository.save(new Nota("201","301","56", 7.5, LocalDate.of(2019,06,20)));
        grades = StreamSupport.stream(notaRepository.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(grades.size(), s+1);
        this.service.deleteNota("201");
        this.service.deleteTema("56");
        this.service.deleteStudent("301");

    }

    @Test
    public void bigBang() {
        addStudent();
        addAssignment();
        addGrade();
    }

    @Test
    public void incremental1() {
        addStudent();
    }

    @Test
    public void incremental2() {
        addStudent();
        addAssignment();
    }
    @Test
    public void incremental3() {
        addStudent();
        addAssignment();
        addGrade();
    }
}
