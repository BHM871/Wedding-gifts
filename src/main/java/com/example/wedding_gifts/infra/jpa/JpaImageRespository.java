package com.example.wedding_gifts.infra.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.wedding_gifts.core.domain.model.Image;
import java.util.List;
import java.util.Optional;


public interface JpaImageRespository extends JpaRepository<Image, UUID> {

    public Optional<Image> findById(UUID id);

    public Optional<Image> findByPathImage(String pathImage);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM tb_image " +
                                        "WHERE gift_id = :gift")
    public List<Image> findAllByGift(@Param("gift") UUID gift);
    
    @Query(nativeQuery = true, value = "DELETE " +
                                        "FROM tb_image " +
                                        "WHERE gift_id = :gift")
    public void deleteAllByGift(@Param("gift") UUID gift);
    
}
