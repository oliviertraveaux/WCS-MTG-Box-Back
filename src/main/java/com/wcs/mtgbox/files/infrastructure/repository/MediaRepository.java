package com.wcs.mtgbox.files.infrastructure.repository;

import com.wcs.mtgbox.files.domain.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
