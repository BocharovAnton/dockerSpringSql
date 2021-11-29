package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, String> {
//    @Query("select  from Users u where u.email like '%@gmail.com%'")//если этого мало можно написать
//        //собственный запрос на языке похожем на SQL
//    List<LectureEntity> findWhereEmailIsGmail(String id);
    Lecture findById(int id);

}