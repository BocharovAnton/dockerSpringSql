package com.example.dockerspringbootpostgres;

import java.time.LocalDateTime;
import java.util.*;

import com.example.dockerspringbootpostgres.ElasticRepository.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.Entity.*;
import com.example.dockerspringbootpostgres.repository.*;

import com.example.dockerspringbootpostgres.Service.DataGenerator;
import com.example.dockerspringbootpostgres.Service.UniversityService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


@SpringBootApplication
public class Application {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private LectureFullTextRepository lectureFullTextRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SpecialityRepository specialityRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private UniversityService service;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private GroupMongoRepository groupMongoRepository;
    @Autowired
    private StudentMongoRepository studentMongoRepository;
    @Autowired
    private RestHighLevelClient client;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup(){
        int[] startDate = new int[5];
        int[] endDate = new int[5];
        String str;
        entryData();
//        Scanner in = new Scanner(System.in);
//        System.out.print("startDate: ");
//        for (int i = 0; i < 5; i++) {
//            startDate[i] = in.nextInt(); // Заполняем массив элементами, введёнными с клавиатуры
//        }
//        System.out.print("endDate: ");
//        for (int i = 0; i < 5; i++) {
//            endDate[i] = in.nextInt(); // Заполняем массив элементами, введёнными с клавиатуры
//        }
//        System.out.print("string: ");
//        str = in.next();
        //УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        startDate = new int[]{2020, 1, 1, 0, 0};//УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!
        endDate = new int[]{2022, 1, 1, 0, 0};//УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!
        str = "Shared access signature";
        lab1(str, startDate, endDate);
    }
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    HashMap<Integer, Integer> studentsAttendance= new HashMap<>();
    HashMap<Integer, Integer> studentsLectureCount = new HashMap<>();
    public void lab1(String textEntry, int[] startDate, int[]endDate){
        List<LectureFullText> lectureFullTextList = service.elasticSearch(textEntry);
        for(LectureFullText lft:lectureFullTextList) {
            Lecture lecture = lectureRepository.findById(lft.getId());
            List <Timetable> timetableList = timetableRepository.findAllByLectureAndDateBetween(
                    lecture
                    , LocalDateTime.of(startDate[0],startDate[1],startDate[2],startDate[3],startDate[4])
                    , LocalDateTime.of(endDate[0],endDate[1],endDate[2],endDate[3],endDate[4]));
            for(Timetable ttl :timetableList) {
                List<Attendance> attendanceList = attendanceRepository.findAllByTimetable(ttl);
                for(Attendance al:attendanceList) {
                    if(studentsAttendance.get(al.getStudent().getId()) != null) { //проверяем есть ли запись в map
                        if (al.getPresence()) {//проверяем посещал ли он назначенную ему пару
                            studentsAttendance.put(al.getStudent().getId(), studentsAttendance.get(al.getStudent().getId()) + 1);
                        }
                        studentsLectureCount.put(al.getStudent().getId(), studentsLectureCount.get(al.getStudent().getId()) + 1);
                    }
                    else{
                        if (al.getPresence()) {
                            studentsAttendance.put(al.getStudent().getId(), 1);
                        } else {
                            studentsAttendance.put(al.getStudent().getId(), 0);
                        }
                        studentsLectureCount.put(al.getStudent().getId(), 1);
                    }
                }
            }
        }
        for(Integer i:studentsAttendance.keySet()){
            studentsAttendance.put(i, studentsAttendance.get(i)*100/ studentsLectureCount.get(i));
            //пересчитываем процент посещений
        }
        studentsAttendance  = sortByValue(studentsAttendance);


        System.out.println("-----------");
        System.out.println("Поиск по термину: " + textEntry);
        System.out.println("По дате, с:  " + startDate[0]+"."+startDate[1]+'.'+startDate[2]);
        System.out.println("До:  " + endDate[0]+"."+ endDate[1]+'.'+endDate[2]);
        System.out.println("-----------");
        int count = 0;
        for(Integer i:studentsAttendance.keySet()){
            if(count < 10) {
                System.out.printf(redisRepository.findStudentById(String.valueOf(i)).getFullName());
                System.out.printf("\nНомер зачетки - " + i);
                //System.out.printf("\nГруппа - " + studentRepository.findById(i).getGroup().getGroupCode());
                //System.out.printf("\nНомер зачетки - " + studentRepository.findById(i).getId());
                System.out.println("\n" + studentsAttendance.get(i) + "%");
                System.out.println("---------");
                count ++;
            }
        }
    }
    public void entryData() {
        Random rand = new Random();

        DataGenerator dataGenerator =  new DataGenerator();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Lecture> lectures = new ArrayList<>();


        //сохраняем специальность
        Speciality newSpeciality = new Speciality();
        newSpeciality.setName("Information Technology");
        this.specialityRepository.save(newSpeciality);


        //сохраняем студентов и группы
        for (int i = 0; i < 3; i++) {
            Set<Student> tmpStudents = new HashSet<>();
            for (int j = 0; j < 30; j++) {
                tmpStudents.add(dataGenerator.getStudent());
            }
            students.addAll(tmpStudents);
            Group group = dataGenerator.getGroup(newSpeciality, tmpStudents);
            groups.add(group);
            GroupMongo groupMongo = new GroupMongo();
            StudentMongo studentMongo = new StudentMongo();
            this.groupRepository.save(group);
            groupMongo.setCode(group.getGroupCode());
            groupMongo.setId(group.getId());
            Set<StudentMongo> studentMongoSet= new HashSet<>();
            StudentRedis studentRedis = new StudentRedis();
            tmpStudents.forEach(student-> {
                student.setId(dataGenerator.GetNextStudentCode());
                student.setGroup(group);
                studentRedis.setId(student.getId());
                studentRedis.setFullName(dataGenerator.getRandomName());
                redisRepository.save(studentRedis);//сохраняем в редис
                studentRepository.save(student);//сохраняем в реляционку
                studentMongoSet.add(new StudentMongo(studentRedis.getId(), studentRedis.getFullName()));
            });
            groupMongo.setStudentList(studentMongoSet);
            studentMongoSet.forEach(studentMongo1 -> studentMongoRepository.save(studentMongo1));
            groupMongoRepository.save(groupMongo);
            System.out.println(groupMongo.toString());
        }
        //добавляем курс
        Course newCourse = new Course();
        newCourse.setSpeciality(newSpeciality);
        newCourse.setSize(50);
        this.courseRepository.save(newCourse);


        //добавляем предмет
        Subject newSubject = new Subject();
        newSubject.setCourse(newCourse);
        newSubject.setName("Принципы построения, проектирования и эксплуатации информационных систем");
        this.subjectRepository.save(newSubject);

        //добавляем лекции и их полный текст в эластик
        for(int i = 0; i < dataGenerator.getLectures().size(); i++){
            Lecture newLecture = new Lecture();
            newLecture.setSubject(newSubject);
            lectures.add(newLecture);
            this.lectureRepository.save(newLecture);

            LectureFullText newFullTextLecture = new LectureFullText();
            newFullTextLecture.id = newLecture.getId();
            newFullTextLecture.text = dataGenerator.getLectures().get(i);
            this.lectureFullTextRepository.save(newFullTextLecture);

        }
        //добавляем расписание (привязываем лекции ко времени и указываем группу)
        for(int i = 0; i < 100; i++){
            Timetable newTimeTable = new Timetable();
            newTimeTable.setDate(LocalDateTime.now());
            newTimeTable.setLecture(lectures.get(rand.nextInt(lectures.size())));
            Group tmpGroup = groups.get(rand.nextInt(groups.size()));
            newTimeTable.setGroup(tmpGroup);
            this.timetableRepository.save(newTimeTable);


            Set<Attendance> attendanceSet = new HashSet<>();
            Set<Student> studentsOfNeededGroup = tmpGroup.getStudentsList();
            studentsOfNeededGroup.forEach(student -> {
                Attendance newAttendance = new Attendance();
                newAttendance.setPresence(rand.nextBoolean());
                newAttendance.setStudent(student);
                newAttendance.setTimetable(newTimeTable);
                this.attendanceRepository.save(newAttendance);
                }
            );
        }
    }
}
