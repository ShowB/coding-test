package com.showb.codingtest.kakaomobility.test3.HappyInc;

import java.io.IOException;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String... args) {

    }

    public class MigrationService {
        private PersistenceManager persistenceManager;

        private static final String POSTFIX_COPY = "-copy";

        public MigrationService(PersistenceManager persistenceManager) {
            this.persistenceManager = persistenceManager;
        }

        public void migrate(Being object, Group group, Type type) {
            try {
                if (type == Type.COPY_SINGLE) {
                    this.copySingle(object, group);
                    return;
                }

                if (type == Type.MOVE_SINGLE) {
                    this.moveSingle(object, group);
                    return;
                }

                if (type == Type.COPY_MANY) {
                    this.copyMany(object, group);
                    return;
                }

                if (type == Type.MOVE_MANY) {
                    this.moveMany(object, group);
                }
            } catch (IOException ignored) {}
        }

        private Being copySingle(Being object, Group group) throws IOException {
            String name = object.getName() + this.POSTFIX_COPY;

            if (object instanceof Product) {
                return persistenceManager.createProduct(name, group);
            }

            return persistenceManager.createCategory(name, group);
        }

        private void moveSingle(Being object, Group group) throws IOException {
            String originName = object.getName();

            if (object instanceof Product originProduct) {
                Product newProduct = (Product) this.copySingle(object, group);
                persistenceManager.deleteProduct(originProduct);
                persistenceManager.updateProduct(newProduct, originName);
                return;
            }

            if (object instanceof Category originCategory) {
                if (!persistenceManager.getProducts(originCategory).isEmpty()) {
                    throw new IOException("category has products...");
                }

                Category newCategory = (Category) this.copySingle(object, group);
                persistenceManager.deleteCategory(originCategory);
                persistenceManager.updateCategory(newCategory, originName);
            }
        }

        private Being copyMany(Being object, Group group) throws IOException {
            return null;
        }

        private void moveMany(Being object, Group group) throws IOException {}

        public enum Type {
            COPY_SINGLE,
            MOVE_SINGLE,
            COPY_MANY,
            MOVE_MANY
        }
    }



    //--- 제공되는 기본 클래스 및 인터페이스 (구현은 되어있다고 가정) ---
    interface Being {
        String getName();
    }
    class Product implements Being {
        private String name;
        public Product(String name) { this.name = name; }
        public String getName() { return name; }
    }
    class Category implements Being {
        private String name;
        public Category(String name) { this.name = name; }
        public String getName() { return name; }
    }
    class Group {
        private String name;
        public Group(String name) { this.name = name; }
    }
    interface PersistenceManager {
        Category createCategory(String name, Group g) throws IOException;
        Product createProduct(String name, Group g) throws IOException;
        void addProductToCategory(Product p, Category c);
        void deleteProduct(Product p);
        void deleteCategory(Category c);
        void updateProduct(Product p, String newName);
        void updateCategory(Category c, String newName);
        List<Category> getCategories(Product p);
        List<Product> getProducts(Category c);
    }
}
