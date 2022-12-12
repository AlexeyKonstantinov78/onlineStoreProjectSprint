package com.example.projectinternetshop.services;

import com.example.projectinternetshop.repositories.ProductRepository;
import com.example.projectinternetshop.models.Image;
import com.example.projectinternetshop.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductService {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // получаем как внесены в базу
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    // получаем все продукты по сортирвке id по убыванию используем при заполнении
    public List<Product> getAllProductSortId() {
//        return productRepository.findAll();
        return productRepository.findAllOrderById();
    }

    // получаем товар по ID
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // поиск фильтрация сортировка
    // поиск по наменованию товара


    public List<Product> searchName(String name) {
        return productRepository.findByTitleContainingIgnoreCase(name);
    }

    // поиск по наменованию товара и фильтрация по диапазону цен
    public List<Product> searchNameFilterOtAndDo(String name, float ot, float Do) {
        System.out.println("ot: " + ot);
        System.out.println("Do: " + Do);
        return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(name, ot, Do);
    }

   // поиск по наменованию товара и фильтрация по диапазону цен сортировка по возрастанию
    public List<Product> searchNameFilterOtAndDoSortedPriceAsc(String name, float ot, float Do) {
        return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanOOrderByPrice(name, ot, Do);
    }

    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по убыванию
    public List<Product> searchNameFilterOtAndDoSortedPriceDesc(String name, float ot, float Do) {
        return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanOOrderByPriceDesc(name, ot, Do);
    }

    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по возрастанию , фильтрация по категории
    public List<Product> searchNameFilterOtAndDoCategorySortedPrice(String name, float ot, float Do, int category) {
        return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanOAndCategoryOrderByPrice(name, ot, Do, category);
    }

    // поиск по наменованию товара и фильтрация по диапазону цен сортировка по убыванию, фильтрация по категории

    public List<Product> searchNameFilterOtAndDoCategorySortedPriceDesc(String name, float ot, float Do, int category) {
        return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanOAndCategoryOrderByPriceDesc(name, ot, Do, category);
    }

    //  поиск -> интервал цен от и до -> катеогрии
    public List<Product> searchNameFilterPriceOtAndDoAndCategory(String name, float ot, float Do, int category) {
        return productRepository.findByTitleContainingIgnoreCasePriceGreaterThanEqualAndPriceLessThanOAndCategory(name, ot, Do, category);
    }

    // запись файлов img к продуктам
    @Transactional
    public void addImageProduct(List<MultipartFile> file_one, Product product) {
        System.out.println(file_one.size());
        System.out.println(product.toString());
        if (file_one.size() > 0 || file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            //цикл
//            for (int i =0 ; i < file_one.size(); i++ ) {
//                String uuidFile = UUID.randomUUID().toString();
//                String rezultFileName = uuidFile + "." + file_one.get(i).getOriginalFilename();
//                file_one.get(i).transferTo(new File(uploadPath + "/" + rezultFileName));
//                Image image = new Image();
//                image.setProduct(product);
//                image.setFileName(rezultFileName);
//                product.addImageProduct(image);
//            }
            //перебором
            for (MultipartFile item : file_one) {
                System.out.println("Добавляем фото" + item.getSize());
                if (item.getSize() > 0 ) {
                    String uuidFile = UUID.randomUUID().toString();
                    String rezultFileName = uuidFile + "." + item.getOriginalFilename();
                    try {
                        item.transferTo(new File(uploadPath + "/" + rezultFileName));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Image image = new Image();
                    image.setProduct(product);
                    image.setFileName(rezultFileName);

                    System.out.println(image.toString());
                    product.addImageProduct(image);
                }
            }
        }
//     обработка одного файла
//        if (file_one != null) {
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }

//            String uuidFile = UUID.randomUUID().toString();
//            String rezultFileName = uuidFile + "." + file_one.getOriginalFilename();
//            file_one.transferTo(new File(uploadPath + "/" + rezultFileName));
//            Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(rezultFileName);
//            product.addImageProduct(image);
//        }

    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    // метод обновляет о продукции
    @Transactional
    public void updateProduct(int id, Product product) {
        product.setId(id);
        product.setDateTimeOfCreated(LocalDateTime.now());
        productRepository.save(product);
    }
    // удаление
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    // метод позволяет найти товар по намиенование
    public Product getProductFinfByTitle(Product product) {
        return productRepository.findByTitle(product.getTitle()).orElse(null);
    }
}
