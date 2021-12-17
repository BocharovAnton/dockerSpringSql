package com.example.dockerspringbootpostgres;

import com.example.dockerspringbootpostgres.Entity.Elastic.LectureFullText;
import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SpecialityMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SubjectMongo;
import com.example.dockerspringbootpostgres.Entity.Neo.AttendanceNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.LectureNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.TimetableNeo;
import com.example.dockerspringbootpostgres.Entity.Postgre.*;
import com.example.dockerspringbootpostgres.Entity.StudentRedis;
import com.example.dockerspringbootpostgres.Service.DataGenerator;
import com.example.dockerspringbootpostgres.Service.UniversityService;
import com.example.dockerspringbootpostgres.repository.Elastic.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.CourseMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.LectureMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.SpecialityMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.SubjectMongoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.AttendanceNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.LectureNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.TimetableNeoRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.*;
import com.example.dockerspringbootpostgres.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;


@RestController
public class Controller {
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
    private UniversityService service;
    @Autowired
    private RedisRepository redisRepository;
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

    @GetMapping(value="/")
    public String entryData() {
        Random rand = new Random();

        DataGenerator dataGenerator =  new DataGenerator();
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Lecture> lectures = new ArrayList<>();

        List<Speciality> specialityList= new ArrayList<>();


        ArrayList<String> specialities = new ArrayList<String>() {{
            add("Информационные технологии");
            add("Программная инженерия");
        }};
        for (String speciality : specialities) {
            //сохраняем специальность
            Speciality newSpeciality = new Speciality();
            newSpeciality.setName(speciality);
            SpecialityMongo newSpecialityMongo = new SpecialityMongo();
            this.specialityRepository.save(newSpeciality);
            specialityList.add(newSpeciality);

            newSpecialityMongo.setName(newSpeciality.getName());
            newSpecialityMongo.setId(newSpeciality.getId());
            ArrayList<Integer> courses = new ArrayList<Integer>() {{
                add(2);
                add(2);
            }};
            Set<CourseMongo> courseMongoList = new HashSet<>();
            Boolean flag = true;
            for (Integer course : courses) {
                //добавляем курс
                Course newCourse = new Course();
                CourseMongo newCourseMongo = new CourseMongo();

                newCourse.setSpeciality(newSpeciality);
                newCourse.setSize(course);
                this.courseRepository.save(newCourse);
                newCourseMongo.setId(newCourse.getId());
                newCourseMongo.setSize(newCourse.getSize());
                newCourseMongo.setSize(newCourse.getSize());
                courseMongoList.add(newCourseMongo);
                ArrayList<String> subjects = new ArrayList<String>();
                if(flag == true ){
                    subjects.add("Принципы построения, проектирования и эксплуатации информационных систем");
                    flag = false;
                }
                else{
                    subjects.add("Основы антикоррупционной деятельности");
                }
                Set<SubjectMongo> subjectMongoList = new HashSet<>();
                for (String subject : subjects) {
                    //добавляем предмет
                    Subject newSubject = new Subject();
                    SubjectMongo newSubjectMongo = new SubjectMongo();
                    newSubject.setCourse(newCourse);
                    newSubject.setName(subject);
                    this.subjectRepository.save(newSubject);
                    newSubjectMongo.setId(newSubject.getId());
                    newSubjectMongo.setName(newSubject.getName());

                    subjectMongoList.add(newSubjectMongo);

                    //добавляем лекции и их полный текст в эластик
                    Set<LectureMongo> lectureMongoList = new HashSet<>();
                    ArrayList<String> lectureList = new ArrayList<>();
                    if(subject.equals("Принципы построения, проектирования и эксплуатации информационных систем"))
                        lectureList = dataGenerator.getLecturesDocker();
                    else
                        lectureList = dataGenerator.getLecturesCorruption();
                    for (int l = 0; l < lectureList.size(); l++) {
                        Lecture newLecture = new Lecture();
                        newLecture.setSubject(newSubject);
                        lectures.add(newLecture);
                        this.lectureRepository.save(newLecture);

                        lectureMongoList.add(new LectureMongo(newLecture.getId(), lectureList.get(l).split("\n")[0]));
                        LectureFullText newFullTextLecture = new LectureFullText();
                        newFullTextLecture.id = newLecture.getId();
                        newFullTextLecture.text = lectureList.get(l);
                        this.lectureFullTextRepository.save(newFullTextLecture);
                    }
                    lectureMongoRepository.saveAll(lectureMongoList);
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
        Set<Student> tmpStudents = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            tmpStudents.clear();
            for (int j = 0; j < 30; j++) {
                tmpStudents.add(dataGenerator.getStudent());
            }
            Group group = dataGenerator.getGroup(specialityList.get(rand.nextInt(specialityList.size())), tmpStudents);
            groups.add(group);
            this.groupRepository.save(group);
            StudentRedis studentRedis = new StudentRedis();
            tmpStudents.forEach(student-> {
                student.setGroup(group);
                studentRedis.setId(student.getId());
                studentRedis.setFullName(dataGenerator.getRandomName());
                redisRepository.save(studentRedis);//сохраняем в редис
                studentRepository.save(student);//сохраняем в реляционку
            });
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
                Set<Group> groupSet = new HashSet<>();
                for(int k = 0; k < rand.nextInt(groups.size()); k++){
                    groupSet.add(groups.get(k));
                }
                newTimetable.setGroupList(groupSet);
                this.timetableRepository.save(newTimetable);

                TimetableNeo newTimetableNeo = new TimetableNeo();
                newTimetableNeo.setId(newTimetable.getId());
                newTimetableNeo.setDate(newTimetable.getDate());

                Set<AttendanceNeo> attendanceSetNeo = new HashSet<>();
                Set<String> groupCodes = new HashSet<>();
                groupSet.forEach(group -> {
                    groupCodes.add(group.getGroupCode());
                });
                groupSet.forEach(group -> {
                    Set<Student> studentsOfNeededGroup = group.getStudentsList();

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
                });
                newTimetableNeo.setAttendanceList(attendanceSetNeo);
                newTimetableNeo.setLecture(newLectureNeo);
                newTimetableNeo.setGroupList(groupCodes);
                this.timetableNeoRepository.save(newTimetableNeo);
                attendanceSetNeo.clear();
                timetableSetNeo.add(newTimetableNeo);

            }
            newLectureNeo.setTimetableList(timetableSetNeo);
            newLectureNeo.setSpecial(true);
            this.lectureNeoRepository.save(newLectureNeo);
            timetableSetNeo.clear();
        }
//        int[] startDate = new int[5];
//        int[] endDate = new int[5];
//        String str;
//        Scanner in = new Scanner(System.in);
//        System.out.print("startDate: ");
//        for (int i = 0; i < 5; i++) {
//            startDate[i] = in.nextInt();
//        }
//        System.out.print("endDate: ");
//        for (int i = 0; i < 5; i++) {
//            endDate[i] = in.nextInt();
//        }
        lab1();
        lab2();
        return "okay";


    }
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        list.sort(Map.Entry.comparingByValue());

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    HashMap<Integer, Integer> studentsAttendance= new HashMap<>();
    HashMap<Integer, Integer> studentsLectureCount = new HashMap<>();


    @GetMapping(value="/lab1")
    public void lab1(){
        studentsAttendance.clear();
        studentsLectureCount.clear();
        String textEntry;
        textEntry = "Azure IoT Hub";
        Integer[] startDate = {2020,1,1,0,0};
        Integer[] endDate = {2022,1,1,0,0};
        List<LectureFullText> lectureFullTextList = service.elasticSearch(textEntry);
        for(LectureFullText lft:lectureFullTextList) {
            LectureNeo lectureNeo = lectureNeoRepository.findById(lft.getId());
            lectureNeo.getTimetableList().forEach(timetableNeo -> {
                if((timetableNeo.getDate().isAfter(LocalDateTime.of(
                        startDate[0],
                        startDate[1],
                        startDate[2],
                        startDate[3],
                        startDate[4])
                ))
                        &&
                        ((timetableNeo.getDate().isBefore(LocalDateTime.of(
                                endDate[0],
                                endDate[1],
                                endDate[2],
                                endDate[3],
                                endDate[4])
                        )))
                ){
                    timetableNeo.getAttendanceList().forEach(attendanceNeo -> {
                        if(studentsAttendance.get(attendanceNeo.getStudent()) != null) { //проверяем есть ли запись в map
                            if (attendanceNeo.getPresence()) {//проверяем посещал ли он назначенную ему пару
                                studentsAttendance.put(
                                        attendanceNeo.getStudent(),
                                        studentsAttendance.get(attendanceNeo.getStudent()) + 1);
                            }
                            studentsLectureCount.put(
                                    attendanceNeo.getStudent(),
                                    studentsLectureCount.get(attendanceNeo.getStudent()) + 1);
                        }
                        else{
                            if (attendanceNeo.getPresence()) {
                                studentsAttendance.put(attendanceNeo.getStudent(), 1);
                            }
                            else {
                                studentsAttendance.put(attendanceNeo.getStudent(), 0);
                            }
                            studentsLectureCount.put(attendanceNeo.getStudent(), 1);
                        }
                    });
                }
            });

        }
        //пересчитываем процент посещений
        studentsAttendance.replaceAll((i, v) -> v * 100 / studentsLectureCount.get(i));
        studentsAttendance  = sortByValue(studentsAttendance);
        System.out.println("-----------");
        System.out.println("Поиск по термину: " + textEntry);
        System.out.println("По дате, с:  " + startDate[0]+"."+startDate[1]+'.'+startDate[2]);
        System.out.println("До:  " + endDate[0]+"."+ endDate[1]+'.'+endDate[2]);
        System.out.println("-----------");
        int count = 0;
        for(Integer i:studentsAttendance.keySet()){
            if(count < 10) {
                System.out.print(redisRepository.findStudentById(String.valueOf(i)).getFullName());
                System.out.print("\nНомер зачетки - " + i);
                System.out.println("\n"+"Процент посещения - "+ studentsAttendance.get(i) + "%");
                System.out.println("---------");
                count ++;
            }
        }
    }
    @GetMapping(value="/lab2")
    public void lab2() {
        int semester = 5;
        int year = 2020;
        LocalDateTime startDate;
        LocalDateTime endDate;
        final Integer[] maxStudentsCount = {0};
        switch (semester) {
            case  (1):
                startDate = LocalDateTime.of(year, 9, 1,0,0);
                endDate = LocalDateTime.of(year+1, 1, 30,0,0);
                break;
            case (2):
                startDate = LocalDateTime.of(year, 2, 10,0,0);
                endDate = LocalDateTime.of(year, 6, 30,0,0);
                break;
            default:
                startDate = LocalDateTime.of(2020, 1, 1,0,0);
                endDate = LocalDateTime.of(2022, 1, 1,0,0);;
                break;
        }
        System.out.println("Учебный год - " + year);
        System.out.println("Семестр - " + semester);
        CourseMongo courseMongo = new CourseMongo();
        courseMongo = courseMongoRepository.findAll().get(0);
        courseMongo.getSubjectList().forEach(subjectMongo -> {
            subjectMongo.getLectureList().forEach(lectureMongo -> {
                timetableRepository.findAllByLectureId(lectureMongo.getId()).forEach(timetable -> {
                    final Integer[] studentsOnLectureCount = {0};
                    if((timetable.getDate().isAfter(startDate))&&(timetable.getDate().isBefore(endDate))){
                        timetable.getGroupList().forEach(group ->
                                studentsOnLectureCount[0] += group.getStudentsList().size());
                        if(studentsOnLectureCount[0] > maxStudentsCount[0]){
                            maxStudentsCount[0] = studentsOnLectureCount[0];
                        }
                    }
                });
            });
        });
        System.out.println("Информация о курсе:" + courseMongo.toString());
        System.out.println("Необходимое кол-во посадочных мест в аудитории:" + maxStudentsCount[0].toString());
    }
    @GetMapping(value="/lab3")
    public void lab3() {
        System.out.println("---------");
        studentsAttendance.clear();
        studentsLectureCount.clear();
        String groupCode = "РСБО";
        Set<CourseMongo> courseSet = new HashSet<>();
        service.findByGroup(groupCode).forEach( timetableNeo -> {
            if(timetableNeo.getLecture().isSpecial()){
                CourseMongo course = service.findByLecture(lectureMongoRepository.findById(timetableNeo.getLecture().getId()).get());
                if(course !=null){
                    courseSet.add(course);
                }
                timetableNeo.getAttendanceList().forEach(attendanceNeo -> {
                    if (studentsAttendance.get(attendanceNeo.getStudent()) != null) { //проверяем есть ли запись в map
                        if (attendanceNeo.getPresence()) {//проверяем посещал ли он назначенную ему пару
                            studentsAttendance.put(
                                    attendanceNeo.getStudent(),
                                    studentsAttendance.get(attendanceNeo.getStudent()) + 2);
                        }
                        studentsLectureCount.put(
                                attendanceNeo.getStudent(),
                                studentsLectureCount.get(attendanceNeo.getStudent()) + 2);
                    } else {
                        if (attendanceNeo.getPresence()) {
                            studentsAttendance.put(attendanceNeo.getStudent(), 2);
                        } else {
                            studentsAttendance.put(attendanceNeo.getStudent(), 0);
                        }
                        studentsLectureCount.put(attendanceNeo.getStudent(), 2);
                    }
                });
            }
        });
        System.out.println("Отчет по всем курсам, лекции которых посещали студенты");
        courseSet.forEach(
                courseMongo -> {
                    System.out.println(courseMongo.toString());
                }
        );
        System.out.println("Отчет по группе - " + groupCode);
        for(Integer i:studentsAttendance.keySet()){
            System.out.print(redisRepository.findStudentById(String.valueOf(i)).getFullName());
            System.out.print("\nНомер зачетки - " + i);
            System.out.println("\n"+"Количество посещенных часов - "+ studentsAttendance.get(i));
            System.out.println("\n"+"Количество запланированных часов - "+ studentsLectureCount.get(i));
            System.out.println("---------");
        }
    }
}
