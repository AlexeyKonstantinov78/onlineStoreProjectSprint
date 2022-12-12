package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByTitle(String title);

    @Query(value = "select * from product order by id desc",nativeQuery = true)
    List<Product> findAllOrderById();

    // поиск по наименованию товара в не зависимости от регистра
    List<Product> findByTitleContainingIgnoreCase(String name);

    @Query(value = "select * from product where (LOWER(title)) like %?1%"
            , nativeQuery = true)
    List<Product> findByTitleContainingIgnoreCase2(String name);

    // поиск по наменованию товара и фильтрация по диапазону цен
    @Query(value =
            "select * from product " +
                    "where " +
                    "((lower(title) like %?1%) or (lower(title) like '?1%') or (lower(title) like '%?1')) and (price between ?2 and ?3)"
            , nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThan(String title, float ot, float Do);

    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по возрастанию
    @Query(value =
            "select * from product " +
                    "where " +
                    "((lower(title) like %?1%) or (lower(title) like '?1%') or (lower(title) like '%?1')) and" +
                    "(price between ?2 and ?3) order by price asc"
            , nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanOOrderByPrice(String title, float ot, float Do);

    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по убыванию
    @Query(value =
            "select * from product " +
                    "where " +
                    "((lower(title) like %?1%) or (lower(title) like '?1%') or (lower(title) like '%?1')) and (price between ?2 and ?3) order by price desc"
            , nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanOOrderByPriceDesc(String title, float ot, float Do);

    // с категориями
    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по возрастанию , фильтрация по категории
    @Query(value =
            "select * from product " +
                    "where " +
                    "((lower(title) like %?1%) or (lower(title) like '?1%') or (lower(title) like '%?1')) and" +
                    "(price between ?2 and ?3) and category_id = ?4 order by price asc"
            , nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanOAndCategoryOrderByPrice(String title, float ot, float Do, int category);

    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по убыванию, фильтрация по категории
    @Query(value =
            "select * from product " +
                    "where " +
                    "((lower(title) like %?1%) or (lower(title) like '?1%') or (lower(title) like '%?1')) and" +
                    "(price between ?2 and ?3) and category_id = ?4 order by price desc "
            , nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanOAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);


    // поиск -> интервал цен от и до -> катеогрии
    @Query(value =
            "select * from product " +
                    "where " +
                    "((lower(title) like %?1%) or (lower(title) like '?1%') or (lower(title) like '%?1')) and" +
                    "(price between ?2 and ?3) and category_id = ?4"
            , nativeQuery = true)
    List<Product> findByTitleContainingIgnoreCasePriceGreaterThanEqualAndPriceLessThanOAndCategory(String title, float ot, float Do, int category);

}
