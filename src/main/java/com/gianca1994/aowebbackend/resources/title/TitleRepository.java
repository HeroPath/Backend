package com.gianca1994.aowebbackend.resources.title;

import com.gianca1994.aowebbackend.resources.title.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
}
