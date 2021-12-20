package com.example.dockerspringbootpostgres;

import com.example.dockerspringbootpostgres.Entity.Elastic.LectureFullText;
import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SpecialityMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SubjectMongo;
import com.example.dockerspringbootpostgres.Entity.Neo.*;
import com.example.dockerspringbootpostgres.Entity.Postgre.*;
import com.example.dockerspringbootpostgres.Entity.StudentRedis;
import com.example.dockerspringbootpostgres.Service.DataGenerator;
import com.example.dockerspringbootpostgres.Service.UniversityService;
import com.example.dockerspringbootpostgres.repository.Elastic.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.CourseMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.LectureMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.SpecialityMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.SubjectMongoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.*;
import com.example.dockerspringbootpostgres.repository.Postgre.*;
import com.example.dockerspringbootpostgres.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    private GroupNeoRepository groupNeoRepository;
    @Autowired
    private StudentNeoRepository studentNeoRepository;
    @Autowired
    private AttendanceNeoRepository attendanceNeoRepository;
    @Autowired
    private TimetableNeoRepository timetableNeoRepository;

    @GetMapping(value="/")
    public String entryData() {

        attendanceRepository.createAttendanceTable();
        Random rand = new Random();

        DataGenerator dataGenerator =  new DataGenerator();
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Lecture> lectures = new ArrayList<>();

        List<Speciality> specialityList= new ArrayList<>();


        ArrayList<String> specialities = new ArrayList<String>() {{
            add("Информационные технологии");
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
            boolean flag = true;
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
                if(flag){
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
                    for (String s : lectureList) {
                        Lecture newLecture = new Lecture();
                        newLecture.setSubject(newSubject);
                        lectures.add(newLecture);
                        this.lectureRepository.save(newLecture);

                        lectureMongoList.add(new LectureMongo(newLecture.getId(), s.split("\n")[0]));
                        LectureFullText newFullTextLecture = new LectureFullText();
                        newFullTextLecture.id = newLecture.getId();
                        newFullTextLecture.text = s;
                        this.lectureFullTextRepository.save(newFullTextLecture);
                    }
                    newSubjectMongo.setLectureList(lectureMongoList);
                }
                newCourseMongo.setSubjectList(subjectMongoList);
            }
            newSpecialityMongo.setCourseList(courseMongoList);
            specialityMongoRepository.save(newSpecialityMongo);
        }


        //сохраняем студентов и группы
        Set<Student> tmpStudents = new HashSet<>();
        Set<StudentNeo> tmpStudentsNeo = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            tmpStudents.clear();
            tmpStudentsNeo.clear();
            for (int j = 0; j < 30; j++) {
                Student tmpStudent = dataGenerator.getStudent();
                tmpStudents.add(tmpStudent);
                StudentNeo tmpStudentNeo = new StudentNeo();
                tmpStudentNeo.setFullName(tmpStudent.getFullName());
                tmpStudentNeo.setId(tmpStudent.getId());
                tmpStudentsNeo.add(tmpStudentNeo);
                this.studentNeoRepository.save(tmpStudentNeo);
            }
            Group group = dataGenerator.getGroup(specialityList.get(rand.nextInt(specialityList.size())), tmpStudents);
            GroupNeo groupNeo = new GroupNeo();
            groupNeo.setGroupCode(group.getGroupCode());
            groupNeo.setStudentsList(tmpStudentsNeo);
            groups.add(group);
            this.groupRepository.save(group);
            groupNeo.setId(group.getId());
            this.groupNeoRepository.save(groupNeo);
            StudentRedis studentRedis = new StudentRedis();
            StudentNeo studentNeo = new StudentNeo();
            tmpStudents.forEach(student-> {
                student.setGroup(group);
                studentRepository.save(student);//сохраняем в реляционку

                studentRedis.setId(student.getId());
                studentRedis.setFullName(student.getFullName());
                this.redisRepository.save(studentRedis);//сохраняем в редис

            });
        }

        for(int j = 0; j < lectures.size(); j++){
            Lecture tmpLecture = lectures.get(j);
            LectureNeo newLectureNeo = new LectureNeo();
            newLectureNeo.setId(tmpLecture.getId());
            Set<TimetableNeo> timetableSetNeo = new HashSet<>();
            Set<Integer> attendanceIdSet = new HashSet<>();

            //добавляем расписание (привязываем лекции ко времени и указываем группу)
            for(int i = 0; i < 10; i++){
                Timetable newTimetable = new Timetable();
                newTimetable.setDate(LocalDateTime.now());
                newTimetable.setLecture(lectures.get(rand.nextInt(lectures.size())));
                Set<Group> groupSet = new HashSet<>();
                Set<GroupNeo> groupNeoSet = new HashSet<>();

                for(int k = 0; k < rand.nextInt(groups.size())+1; k++){
                    Group tmpGroup = groups.get(k);
                    groupSet.add(tmpGroup);
                    groupNeoSet.add(groupNeoRepository.findById(tmpGroup.getId()));
                }
                newTimetable.setGroupList(groupSet);
                this.timetableRepository.save(newTimetable);

                TimetableNeo newTimetableNeo = new TimetableNeo();
                newTimetableNeo.setId(newTimetable.getId());
                newTimetableNeo.setDate(newTimetable.getDate());
                newTimetableNeo.setGroupList(groupNeoSet);

                groupSet.forEach(group -> {
                    Set<Student> studentsOfNeededGroup = group.getStudentsList();

                    studentsOfNeededGroup.forEach(student -> {
                                Attendance newAttendance = new Attendance();
                                newAttendance.setPresence(rand.nextBoolean());
                                newAttendance.setStudent(student);
                                newAttendance.setTimetable(newTimetable);
                                newAttendance.setDate(newTimetable.getDate());
                                this.attendanceRepository.save(newAttendance);
                                attendanceIdSet.add(newAttendance.getId());
                            }
                    );
                });
                newTimetableNeo.setLecture(newLectureNeo);
                newTimetableNeo.setAttendanceList(attendanceIdSet);
                this.timetableNeoRepository.save(newTimetableNeo);
                timetableSetNeo.add(newTimetableNeo);

            }
            newLectureNeo.setTimetableList(timetableSetNeo);
            newLectureNeo.setSpecial(true);
            this.lectureNeoRepository.save(newLectureNeo);
            timetableSetNeo.clear();
        }
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
                    timetableNeo.getAttendanceList().forEach(attendanceId -> {
                        Attendance attendance = attendanceRepository.findById(attendanceId);
                        if(studentsAttendance.get(attendance.getStudent().getId()) != null) { //проверяем есть ли запись в map
                            if (attendance.getPresence()) {//проверяем посещал ли он назначенную ему пару
                                studentsAttendance.put(
                                        attendance.getStudent().getId(),
                                        studentsAttendance.get(attendance.getStudent().getId()) + 1);
                            }
                            studentsLectureCount.put(
                                    attendance.getStudent().getId(),
                                    studentsLectureCount.get(attendance.getStudent().getId()) + 1);
                        }
                        else{
                            if (attendance.getPresence()) {
                                studentsAttendance.put(attendance.getStudent().getId(), 1);
                            }
                            else {
                                studentsAttendance.put(attendance.getStudent().getId(), 0);
                            }
                            studentsLectureCount.put(attendance.getStudent().getId(), 1);
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
//        int semester = 1;
//        int year = 2021;
//        LocalDateTime startDate;
//        LocalDateTime endDate;
//        final Integer[] maxStudentsCount = {0};
//        switch (semester) {
//            case  (1):
//                startDate = LocalDateTime.of(year, 9, 1,0,0);
//                endDate = LocalDateTime.of(year+1, 1, 30,0,0);
//                break;
//            case (2):
//                startDate = LocalDateTime.of(year, 2, 10,0,0);
//                endDate = LocalDateTime.of(year, 6, 30,0,0);
//                break;
//            default:
//                startDate = LocalDateTime.of(2020, 1, 1,0,0);
//                endDate = LocalDateTime.of(2022, 1, 1,0,0);;
//                break;
//        }
//        System.out.println("Учебный год - " + year);
//        System.out.println("Семестр - " + semester);
//        CourseMongo courseMongo = new CourseMongo();
//        courseMongo = courseMongoRepository.findAll().get(0);
//        courseMongo.getSubjectList().forEach(subjectMongo -> {
//            subjectMongo.getLectureList().forEach(lectureMongo -> {
//                timetableRepository.findAllByLectureId(lectureMongo.getId()).forEach(timetable -> {
//                    final Integer[] studentsOnLectureCount = {0};
//                    if((timetable.getDate().isAfter(startDate))&&(timetable.getDate().isBefore(endDate))){
//                        timetable.getGroupList().forEach(group ->
//                                studentsOnLectureCount[0] += group.getStudentsList().size());
//                        if(studentsOnLectureCount[0] > maxStudentsCount[0]){
//                            maxStudentsCount[0] = studentsOnLectureCount[0];
//                        }
//                    }
//                });
//            });
//        });
//        System.out.println("Информация о курсе:" + courseMongo.toString());
//        System.out.println("Необходимое кол-во посадочных мест в аудитории:" + maxStudentsCount[0].toString());
    }
    @GetMapping(value="/lab3")
    public void lab3() {
//        System.out.println("---------");
//        studentsAttendance.clear();
//        studentsLectureCount.clear();
//        String groupCode = "РИБО";
//        Set<CourseMongo> courseSet = new HashSet<>();
//        service.findByGroup(groupCode).forEach( timetableNeo -> {
//            if(timetableNeo.getLecture().isSpecial()){
//                CourseMongo course = service.findByLecture(lectureMongoRepository.findById(timetableNeo.getLecture().getId()).get());
//                if(course !=null){
//                    courseSet.add(course);
//                }
//                timetableNeo.getAttendanceList().forEach(attendanceNeo -> {
//                    if (studentsAttendance.get(attendanceNeo.getStudent()) != null) { //проверяем есть ли запись в map
//                        if (attendanceNeo.getPresence()) {//проверяем посещал ли он назначенную ему пару
//                            studentsAttendance.put(
//                                    attendanceNeo.getStudent(),
//                                    studentsAttendance.get(attendanceNeo.getStudent()) + 2);
//                        }
//                        studentsLectureCount.put(
//                                attendanceNeo.getStudent(),
//                                studentsLectureCount.get(attendanceNeo.getStudent()) + 2);
//                    } else {
//                        if (attendanceNeo.getPresence()) {
//                            studentsAttendance.put(attendanceNeo.getStudent(), 2);
//                        } else {
//                            studentsAttendance.put(attendanceNeo.getStudent(), 0);
//                        }
//                        studentsLectureCount.put(attendanceNeo.getStudent(), 2);
//                    }
//                });
//            }
//        });
//        System.out.println("Отчет по всем курсам, лекции которых посещали студенты");
//        courseSet.forEach(
//                courseMongo -> {
//                    System.out.println(courseMongo.toString());
//                }
//        );
//        System.out.println("Отчет по группе - " + groupCode);
//        for(Integer i:studentsAttendance.keySet()){
//            System.out.print(redisRepository.findStudentById(String.valueOf(i)).getFullName());
//            System.out.print("\nНомер зачетки - " + i);
//            System.out.println("\n"+"Количество посещенных часов - "+ studentsAttendance.get(i));
//            System.out.println("\n"+"Количество запланированных часов - "+ studentsLectureCount.get(i));
//            System.out.println("---------");
//        }
    }
}
