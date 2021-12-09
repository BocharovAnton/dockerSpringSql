package com.example.dockerspringbootpostgres;

import java.time.LocalDateTime;
import java.util.*;

import com.example.dockerspringbootpostgres.repository.Elastic.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.Entity.*;
import com.example.dockerspringbootpostgres.Entity.Elastic.LectureFullText;
import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SpecialityMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SubjectMongo;
import com.example.dockerspringbootpostgres.Entity.Neo.AttendanceNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.LectureNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.TimetableNeo;
import com.example.dockerspringbootpostgres.Entity.Postgre.*;
import com.example.dockerspringbootpostgres.repository.*;

import com.example.dockerspringbootpostgres.Service.DataGenerator;
import com.example.dockerspringbootpostgres.Service.UniversityService;
import com.example.dockerspringbootpostgres.repository.Mongo.*;
import com.example.dockerspringbootpostgres.repository.Neo.AttendanceNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.LectureNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.TimetableNeoRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.*;
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
    private SpecialityMongoRepository specialityMongoRepository;
    @Autowired
    private CourseMongoRepository courseMongoRepository;
    @Autowired
    private SubjectMongoRepository subjectMongoRepository;
    @Autowired
    private LectureMongoRepository lectureMongoRepository;
    @Autowired
    private LectureNeoRepository lectureNeoRepository;
    @Autowired
    private AttendanceNeoRepository attendanceNeoRepository;
    @Autowired
    private TimetableNeoRepository timetableNeoRepository;
    @Autowired
    private RestHighLevelClient client;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup(){
        int[] startDate = new int[5];
        int[] endDate = new int[5];
        String str;
        entryData();
        Scanner in = new Scanner(System.in);
        System.out.print("startDate: ");
        for (int i = 0; i < 5; i++) {
            startDate[i] = in.nextInt();
        }
        System.out.print("endDate: ");
        for (int i = 0; i < 5; i++) {
            endDate[i] = in.nextInt();
        }
        //УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        startDate = new int[]{2020, 1, 1, 0, 0};//УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        endDate = new int[]{2022, 1, 1, 0, 0};//УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // УБРАТЬ ПОСЛЕ ТЕСТОВ!!!!!!!
        str = "Azure IoT Hub";
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
                System.out.println("\n"+"Процент посещения - "+ studentsAttendance.get(i) + "%");
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

        List<Speciality> specialityList= new ArrayList<>();


        ArrayList<String> specialities = new ArrayList<String>() {{
            add("Информационные технологии");
            add("Программная инженерия");
        }};
        for(int i = 0; i < specialities.size(); i ++ ){
            //сохраняем специальность
            Speciality newSpeciality = new Speciality();
            newSpeciality.setName(specialities.get(i));
            SpecialityMongo newSpecialityMongo  = new SpecialityMongo();
            this.specialityRepository.save(newSpeciality);
            specialityList.add(newSpeciality);

            newSpecialityMongo.setName(newSpeciality.getName());
            newSpecialityMongo.setId(newSpeciality.getId());
            ArrayList<Integer> courses = new ArrayList<Integer>() {{
                add(4);
            }};
            Set<CourseMongo> courseMongoList = new HashSet<>();
            for(int j = 0; j < courses.size(); j++ ) {
                //добавляем курс
                Course newCourse = new Course();
                CourseMongo newCourseMongo = new CourseMongo();

                newCourse.setSpeciality(newSpeciality);
                newCourse.setSize(courses.get(j));
                this.courseRepository.save(newCourse);
                newCourseMongo.setId(newCourse.getId());
                newCourseMongo.setSize(newCourse.getSize());
                newCourseMongo.setSize(newCourse.getSize());
                courseMongoList.add(newCourseMongo);

                ArrayList<String> subjects = new ArrayList<String>() {{
                    add("Принципы построения, проектирования и эксплуатации информационных систем");
                }};

                Set<SubjectMongo> subjectMongoList = new HashSet<>();
                for (int k = 0; k < subjects.size(); k++) {
                    //добавляем предмет
                    Subject newSubject = new Subject();
                    SubjectMongo newSubjectMongo = new SubjectMongo();
                    newSubject.setCourse(newCourse);
                    newSubject.setName(subjects.get(k));
                    this.subjectRepository.save(newSubject);
                    newSubjectMongo.setId(newSubject.getId());
                    newSubjectMongo.setName(newSubject.getName());

                    subjectMongoList.add(newSubjectMongo);

                    //добавляем лекции и их полный текст в эластик
                    Set<LectureMongo> lectureMongoList = new HashSet<>();
                    for(int l = 0; l < dataGenerator.getLectures().size(); l++) {
                        Lecture newLecture = new Lecture();
                        newLecture.setSubject(newSubject);
                        lectures.add(newLecture);
                        this.lectureRepository.save(newLecture);

                        LectureMongo newLectureMongo = new LectureMongo();
                        lectureMongoList.add(new LectureMongo(newLecture.getId(), "TEMP_NAME" + newLecture.getId()));

                        LectureFullText newFullTextLecture = new LectureFullText();
                        newFullTextLecture.id = newLecture.getId();
                        newFullTextLecture.text = dataGenerator.getLectures().get(l);
                        this.lectureFullTextRepository.save(newFullTextLecture);
                    }
                    lectureMongoList.forEach(lectureMongo -> lectureMongoRepository.save(lectureMongo));
                    newSubjectMongo.setLectureList(lectureMongoList);
                    subjectMongoRepository.save(newSubjectMongo);
                }
                newCourseMongo.setSubjectList(subjectMongoList);
                courseMongoRepository.save(newCourseMongo);
            }
            newSpecialityMongo.setCourseList(courseMongoList);
            specialityMongoRepository.save(newSpecialityMongo);
        }


        //сохраняем студентов и группы
        for (int i = 0; i < 3; i++) {
            Set<Student> tmpStudents = new HashSet<>();
            for (int j = 0; j < 30; j++) {
                tmpStudents.add(dataGenerator.getStudent());
            }
            students.addAll(tmpStudents);
            Group group = dataGenerator.getGroup(specialityList.get(rand.nextInt(specialityList.size())), tmpStudents);
            groups.add(group);
            this.groupRepository.save(group);
            //GroupMongo groupMongo = new GroupMongo();
            //StudentMongo studentMongo = new StudentMongo();

            //groupMongo.setCode(group.getGroupCode());
            //groupMongo.setId(group.getId());
            // Set<StudentMongo> studentMongoSet= new HashSet<>();
            StudentRedis studentRedis = new StudentRedis();
            tmpStudents.forEach(student-> {
                student.setId(dataGenerator.GetNextStudentCode());
                student.setGroup(group);
                studentRedis.setId(student.getId());
                studentRedis.setFullName(dataGenerator.getRandomName());
                redisRepository.save(studentRedis);//сохраняем в редис
                studentRepository.save(student);//сохраняем в реляционку
                //studentMongoSet.add(new StudentMongo(studentRedis.getId(), studentRedis.getFullName()));
            });
            //groupMongo.setStudentList(studentMongoSet);
            //studentMongoSet.forEach(studentMongo1 -> studentMongoRepository.save(studentMongo1));
            //groupMongoRepository.save(groupMongo);
            //System.out.println(groupMongo.toString());
        }

        for(int j = 0; j < lectures.size(); j++){
            Lecture tmpLecture = lectures.get(j);
            LectureNeo newLectureNeo = new LectureNeo();
            newLectureNeo.setId(tmpLecture.getId());
            Set<TimetableNeo> timetableSetNeo = new HashSet<>();

            //добавляем расписание (привязываем лекции ко времени и указываем группу)
            for(int i = 0; i < 10; i++){
                Timetable newTimetable = new Timetable();
                newTimetable.setDate(LocalDateTime.now());
                newTimetable.setLecture(lectures.get(rand.nextInt(lectures.size())));

                Group tmpGroup = groups.get(rand.nextInt(groups.size()));
                newTimetable.setGroup(tmpGroup);
                this.timetableRepository.save(newTimetable);

                TimetableNeo newTimetableNeo = new TimetableNeo();
                newTimetableNeo.setId(newTimetable.getId());

                Set<AttendanceNeo> attendanceSetNeo = new HashSet<>();
                Set<Student> studentsOfNeededGroup = tmpGroup.getStudentsList();

                studentsOfNeededGroup.forEach(student -> {
                            Attendance newAttendance = new Attendance();
                            AttendanceNeo newAttendanceNeo = new AttendanceNeo();
                            newAttendance.setPresence(rand.nextBoolean());
                            newAttendance.setStudent(student);
                            newAttendance.setTimetable(newTimetable);
                            this.attendanceRepository.save(newAttendance);

                            newAttendanceNeo.setId(newAttendance.getId());
                            newAttendanceNeo.setStudent(newAttendance.getStudent().getId());
                            newAttendanceNeo.setPresence(newAttendance.getPresence());
                            this.attendanceNeoRepository.save(newAttendanceNeo);
                            attendanceSetNeo.add(newAttendanceNeo);
                        }
                );
                newTimetableNeo.setAttendanceList(attendanceSetNeo);
                this.timetableNeoRepository.save(newTimetableNeo);
                attendanceSetNeo.clear();
                timetableSetNeo.add(newTimetableNeo);
            }
            newLectureNeo.setTimetableList(timetableSetNeo);
            this.lectureNeoRepository.save(newLectureNeo);
            timetableSetNeo.clear();
        }
    }
}
