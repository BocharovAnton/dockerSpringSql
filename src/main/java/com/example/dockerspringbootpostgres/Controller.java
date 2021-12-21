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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequiredArgsConstructor
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
    private final EntityManager entityManager;
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

            newSpecialityMongo.setSpeciality_name(newSpeciality.getName());
            newSpecialityMongo.setSpeciality_id(newSpeciality.getId());
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
                newCourseMongo.setCourse_id(newCourse.getId());
                newCourseMongo.setCourse_size(newCourse.getSize());
                newCourseMongo.setCourse_size(newCourse.getSize());
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
                    newSubjectMongo.setSubject_id(newSubject.getId());
                    newSubjectMongo.setSubject_name(newSubject.getName());

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

        for (int i = 0; i < 3; i++) {
            Set<StudentNeo> tmpStudentsNeo = new HashSet<>();
            Set<Student> tmpStudents = new HashSet<>();
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
            Set<Group> groupSet = new HashSet<>();
            //добавляем расписание (привязываем лекции ко времени и указываем группу)
            for(int i = 0; i < 10; i++){
                attendanceIdSet.clear();
                Timetable newTimetable = new Timetable();
                newTimetable.setDate(LocalDateTime.now());
                newTimetable.setLecture(lectures.get(rand.nextInt(lectures.size())));
                Set<GroupNeo> groupNeoSet = new HashSet<>();
                groupSet.clear();
                for(int k=0; k<groups.size();k++){
                    groupNeoSet.add(groupNeoRepository.findById(groups.get(k).getId()));
                    groupSet.add(groups.get(k));
                }
                newTimetable.setGroupList(groupSet);
                this.timetableRepository.save(newTimetable);

                TimetableNeo newTimetableNeo = new TimetableNeo();
                newTimetableNeo.setId(newTimetable.getId());
                newTimetableNeo.setDate(newTimetable.getDate());
                newTimetableNeo.setGroupList(groupNeoSet);

                for (Group group : groupSet) {
                    Set<Student> studentsOfNeededGroup  = new HashSet<>();
                    studentsOfNeededGroup = group.getStudentsList();
                    for (Student student : studentsOfNeededGroup) {
                        Attendance newAttendance = new Attendance();
                        newAttendance.setPresence(rand.nextBoolean());
                        newAttendance.setStudent(student);
                        newAttendance.setTimetable(newTimetable);
                        newAttendance.setDate(newTimetable.getDate());
                        this.attendanceRepository.save(newAttendance);
                        attendanceIdSet.add(newAttendance.getId());
                    }
                }
                newTimetableNeo.setLecture(newLectureNeo);
                newTimetableNeo.setAttendanceList(attendanceIdSet);
                this.timetableNeoRepository.save(newTimetableNeo);
                timetableSetNeo.add(newTimetableNeo);
            }
            groupSet.forEach(
                    group -> {
                        GroupNeo groupNeo = groupNeoRepository.findById(group.getId());
                        groupNeo.setTimetableList(timetableSetNeo);
                        groupNeoRepository.save(groupNeo);
                    }
            );
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
        Set<StudentNeo> studentsSet = new HashSet<>();
        Set<Attendance> attendanceSet = new HashSet<>();
        String textEntry;
        textEntry = "Azure IoT Hub";
        LocalDateTime startDate = LocalDateTime.of(
                2021,
                9,
                1,
                0,
                0
                );
        LocalDateTime endDate = LocalDateTime.of(
                2021,
                12,
                31,
                0,
                0
        );
        Set<Integer> months = new HashSet<>();
        Integer firstMonth = startDate.getMonthValue();
        Integer lastMonth = endDate.getMonthValue();
        for(;firstMonth<=lastMonth;firstMonth++){
            if(firstMonth>=9){
                months.add(firstMonth-8);
            }
            else{
                months.add(firstMonth+4);
            }
        }
        List<LectureFullText> lectureFullTextList = service.elasticSearch(textEntry);
        for(LectureFullText lft:lectureFullTextList) {
            LectureNeo lectureNeo = lectureNeoRepository.findById(lft.getId());
            lectureNeo.getTimetableList().forEach(timetableNeo -> {
                if((timetableNeo.getDate().isAfter(startDate)) && ((timetableNeo.getDate().isBefore(endDate))))
                {
                    timetableNeo.getGroupList().forEach(
                            groupNeo -> {
                                studentsSet.addAll(groupNeo.getStudentsList());
                            }
                    );
                }
            });
            attendanceSet.clear();
            for(StudentNeo student: studentsSet){
                for(Integer m:months){
                    attendanceSet.addAll(entityManager.createNativeQuery("SELECT * FROM attendance_"+ m +" v WHERE v.student_id = ?1 ", Attendance.class)
                            .setParameter(1, student.getId())
                            .getResultList());
                }
            }
            attendanceSet.forEach(attendance -> {
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
//        пересчитываем процент посещений
        studentsAttendance.replaceAll((i, v) -> v * 100 / studentsLectureCount.get(i));
        studentsAttendance  = sortByValue(studentsAttendance);
        System.out.println("-----------");
        System.out.println("Поиск по термину: " + textEntry);
        System.out.println("По дате, с:  " + startDate.getYear()+" "+startDate.getMonth()+" "+startDate.getDayOfMonth());
        System.out.println("До:  " + endDate.getYear()+" "+ endDate.getMonth()+" "+endDate.getDayOfMonth());
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
        int semester = 1;
        int year = 2021;
        LocalDateTime startDate;
        LocalDateTime endDate;
        Integer maxStudentsCount = 0;
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
        Set<Lecture> lectureSet = new HashSet<>();
        for(SpecialityMongo specialityMongo: specialityMongoRepository.findAll()){
            for(CourseMongo courseMongo: specialityMongo.getCourseList()){
                maxStudentsCount = 0;
                System.out.println("Курс: ");
                System.out.println(courseMongo.toString());
                for(SubjectMongo subjectMongo: courseMongo.getSubjectList()){
                    for(LectureMongo lectureMongo: subjectMongo.getLectureList()){
                        List<Timetable> timetables = new ArrayList<>();
                        timetables = timetableRepository.findAllByLectureAndDateBetween(lectureRepository.findById(lectureMongo.getLecture_id()), startDate, endDate);
                        for(Timetable timetable : timetables){
                            if(maxStudentsCount < timetable.getGroupList().size()*30){
                                maxStudentsCount = timetable.getGroupList().size()*30;
                            }
                        }
                    }
                }
                System.out.println("Необходимый размер аудитории для данного курса: "+maxStudentsCount+" посадочных мест");
            }
        }
    }
    @GetMapping(value="/lab3")
    public void lab3() {
        System.out.println("---------");
        studentsAttendance.clear();
        studentsLectureCount.clear();
        String groupCode = "РИБО";
        Set<CourseMongo> courseSet = new HashSet<>();
        Set<StudentNeo> studentsSet = new HashSet<>();
        Set<Attendance> attendanceSet = new HashSet<>();
        GroupNeo groupNeo = service.findByGroupCode(groupCode);
        if(groupNeo!=null) {
            studentsSet.addAll(groupNeo.getStudentsList());
            Integer minStudentId = 99999999;
            Integer maxStudentId = 0;
            for (StudentNeo studentNeo : studentsSet) {
                if(studentNeo.getId()<minStudentId){
                    minStudentId = studentNeo.getId();
                }
                else
                    if(studentNeo.getId()>maxStudentId){
                        maxStudentId = studentNeo.getId();
                    }
            }
            for (StudentNeo studentNeo : studentsSet) {
                studentsAttendance.put(studentNeo.getId(), 0);
                studentsLectureCount.put(studentNeo.getId(), 0);
            }
            for (TimetableNeo timetableNeo : groupNeo.getTimetableList()) {
                if (timetableNeo.getLecture().isSpecial()) {
                    Integer lectureMonth = timetableNeo.getDate().getMonthValue();
                    if (lectureMonth >= 9)
                        lectureMonth -= 8;
                    else
                        lectureMonth += 4;
                    attendanceSet.clear();
                    attendanceSet.addAll(entityManager.createNativeQuery("SELECT * FROM attendance_" + lectureMonth + " v WHERE v.timetable_id = ?1 AND v.student_id BETWEEN ?2 AND ?3", Attendance.class)
                            .setParameter(1, timetableNeo.getId())
                            .setParameter(2, minStudentId)
                            .setParameter(3, maxStudentId)
                            .getResultList());
                    attendanceSet.forEach(attendance -> {
                        if (studentsAttendance.get(attendance.getStudent().getId()) != null) { //проверяем есть ли запись в map
                            if (attendance.getPresence()) {//проверяем посещал ли он назначенную ему пару
                                studentsAttendance.put(
                                        attendance.getStudent().getId(),
                                        studentsAttendance.get(attendance.getStudent().getId()) + 1);
                            }
                            studentsLectureCount.put(
                                    attendance.getStudent().getId(),
                                    studentsLectureCount.get(attendance.getStudent().getId()) + 1);
                        }
                    });
                }
            }
            System.out.println("Отчет по группе - " + groupCode);
            for (Integer i : studentsAttendance.keySet()) {
                System.out.print(redisRepository.findStudentById(String.valueOf(i)).getFullName());
                System.out.print("\nНомер зачетки - " + i);
                System.out.println("\n" + "Количество посещенных часов - " + studentsAttendance.get(i));
                System.out.println("\n" + "Количество запланированных часов - " + studentsLectureCount.get(i));
                System.out.println("---------");
            }
        }
        else{
            System.out.println("Группа не найдена");
        }
//        service.findByGroup(groupCode).forEach( timetableNeo -> {
//            if(timetableNeo.getLecture().isSpecial()){
//                CourseMongo course = service.findByLecture(lectureMongoRepository.findById(timetableNeo.getLecture().getId()).get());
//                if(course !=null){
//                    courseSet.add(course);
//                }
//                for(StudentNeo student: studentsSet){
//                    for(Integer m:months){
//                        attendanceSet.addAll(entityManager.createNativeQuery("SELECT * FROM attendance_"+ m +" v WHERE v.student_id = ?1", Attendance.class)
//                                .setParameter(1, student.getId())
//                                .getResultList());
//                    }
//                }
//                attendanceSet.forEach(attendance -> {
//                    if(studentsAttendance.get(attendance.getStudent().getId()) != null) { //проверяем есть ли запись в map
//                        if (attendance.getPresence()) {//проверяем посещал ли он назначенную ему пару
//                            studentsAttendance.put(
//                                    attendance.getStudent().getId(),
//                                    studentsAttendance.get(attendance.getStudent().getId()) + 1);
//                        }
//                        studentsLectureCount.put(
//                                attendance.getStudent().getId(),
//                                studentsLectureCount.get(attendance.getStudent().getId()) + 1);
//                    }
//                    else{
//                        if (attendance.getPresence()) {
//                            studentsAttendance.put(attendance.getStudent().getId(), 1);
//                        }
//                        else {
//                            studentsAttendance.put(attendance.getStudent().getId(), 0);
//                        }
//                        studentsLectureCount.put(attendance.getStudent().getId(), 1);
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
