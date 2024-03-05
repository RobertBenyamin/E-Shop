# ADV Shop

Link Deployment: https://robert-benyamin-adv-shop.koyeb.app/

## Module 1

<details>
<summary>Reflection 1</summary>

## Clean Code Principles and Secure Coding Practices

### Clean code

1. Meaningful Names  
   Salah satu penerapan prinsip tersebut terdapat pada file `ProductRepository.java`.

    ```java
    private List<Product> productData = new ArrayList<>();
    ```

    ArrayList tersebut memiliki nama yang mendeskripsikan data apa yang disimpan didalamnya, yaitu Product.

2. Function  
   Salah satu penerapan prinsip tersebut terdapat pada file `ProductController.java`.

    ```java
    @PostMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        service.deleteById(productId);
        return "redirect:/product/list";
    }
    ```

    Fungsi tersebut memiliki nama yang deskriptif. Fungsi tersebut juga hanya melakukan satu hal, yaitu menghapus product.

3. Comments  
   Salah satu penerapan prinsip tersebut terdapat pada file `HomePageFunctionalTest.java`.

    ```java
    /**
     * The port number assigned to the running application during test execution. * Set automatically during each test run by Spring Framework's test context. */
    @LocalServerPort
    private int serverPort;
    ```

    Comment tersebut memberikan informasi tambahan mengenai variabel `serverPort`, yaitu bahwa variabel tersebut menyimpan port number untuk testing.

4. Objects and Data Structure  
   Salah satu penerapan prinsip tersebut terdapat pada file `Product.java`.
    ```java
    @Getter @Setter
    public class Product {
        private String productId;
        private String productName;
        private int productQuantity;
    }
    ```
    Setiap atribut bertipe private sehingga setiap variabel bersifat independen. Setiap atribut tersebut hanya dapat diakses melalui fungsi getter dan setter.

### Secure Coding

Kode saya barulah mengimplementasikan testing untuk memenuhi prinsip secure coding. Testing tersebut terdiri dari 2 jenis, yaitu unit testing dan functional testing.

## Mistake in my code

-   Kode saya masih belum mengimplementasikan error handling yang baik. Peningkatan yang bisa saya lakukan adalah dengan mengimplemntasikan error handling sesuai standar terutama pada fungsi-fungsi seperti create, edit, dan delete product.

-   Cara saya menerapkan ID untuk product masih sangat tidak efektif yang berakibat ke algoritma untuk mencari product menggunakan ID juga tidak efektif. Peningkatan yang bisa saya lakukan adalah menggunakan ID generator kemudian menyimpan data product di database, bukan di ArrayList.

-   Kode saya belum menerapkan validasi input. Peningkatan yang bisa saya lakukan adalah menerapkan validasi input ketika user create dan edit product.

</details>

<details>
<summary>Reflection 2</summary>

1.  -   Untuk saat ini, saya belum terlalu merasakan suatu hal positif dari menulis unit test. Hal ini karena saya belum merasakan langsung manfaat dari adanya test tersebut. Namun, saya merasa kode saya akan lebih aman dari error ketika mengalami perubahan.
    -   Jumlah unit test yang perlu dibuat untuk satu class bergantung pada kompleksitas class tersebut. Semakin kompleks suatu class, maka semakin banyak unit test yang harus dibuat.
    -   Suatu unit tests dikatakan cukup untuk memverifikasi suatu program ketika semua kemungkinan skenario dari program kita telah dihandle oleh suatu unit test.
    -   100% code coverage tidak menjamin bahwa kode kita bebas dari error atau bug. 100% code coverage hanya berarti seluruh bagian kode kita telah dites. Namun suatu bagian kode bisa memiliki beberapa skenario. Bisa saja terdapat kemungkinan skenario yang tidak ditangani oleh test kita.

2.  Menurut saya kode tersebut jadi kurang bersih karena terdapat redundansi kode. Selain itu, jika suatu saat kita perlu melakukan perubahan pada setup prosedur, maka kita perlu melakukan dua kali perubahan. Untuk meningkatkan kode tersebut, kita dapat menempatkan test untuk menghitung jumlah item pada product list di file yang sama sehingga kita tidak perlu menjalankan prosedur setup dua kali.

</details>

## Module 2

<details>
<summary>Reflection</summary>

1. List of Code Quality Issues that have been Fixed

    - Menghapus `access modifier public` dari fungsi fungsi pada `ProductService.java`
    - Menambahkan `token permission` pada `ci.yml`
    - Menghapus import yang tidak terpakai pada `ProductRepositoryTest.java`
    - Menambahkan deskripsi kepada tabel pada `productList.html`

2. Ya, menurut saya implementasi sekarang sudah memenuhi Continuous Integration dan Continuous Deployment. Continuous Integration merupakan praktik untuk mengautomasi proses integrasi dan verifikasi setiap perubahan pada kode kita dengan bantuan alat. Implementasi sekarang sudah memenuhi hal tersebut dengan membuat script untuk menjalankan test suite (`ci.yml`) dan menganalisis isu keamanan (`pmd.yml` & `scorecard.yml`) setiap melakukan push ke repository Github. Continuous Deployment merupakan praktik untuk mengautomasi proses deployment aplikasi kita ke server tertentu. Implementasi sekarang sudah memenuhi hal tersebut dengan mengintegrasikan layanan `Koyeb` ke repository Github, sehingga aplikasi akan otomatis ter-deploy setiap kali melakukan push ke branch main repository Github.

</details>

</details>

## Module 3

<details>
<summary>Reflection</summary>

1. Explain what principles you apply to your project!

    - Single Responsibility Principle (SRP)  
      Saya mengimplementasikan SRP dengan membuat class CarController dan HomeController terpisah dari file ProductController. Hal ini membuat setiap class hanya berinteraksi dengan satu model. Selain itu, saya juga menghapus extends ProductController dari CarController. Hal ini membuat CarController benar benar fokus terhadap model Car saja.
    - Open-Closed Principle (OCP)  
      OCP terdapat pada model Product dan Car. Model Product sudah tertutup terhadap modifikasi. Sehingga jika ingin menambahkan fungsionalitas baru kita buat class baru yang meng-extends Product.
    - Liskov Substitution Principle (LSP)  
      Class *ServiceImpl adalah turunan dari *Service. *ServiceImpl dapat digunakan untuk menggantikan *Service. Hal ini sesuai dengan prinsip LSP dimana subclass dapat digunakan untuk menggantikan parent-nya.
    - Interface Segregation Principle (ISP)  
      Interface pada project ini sudah dipisahkan menjadi ProductService dan CarService sehingga class ProductServiceImpl dan CarServiceImpl dapat mengimplementasikan interface yang mereka perlukan saja.
    - Dependency Inversions Principle (DIP)  
      Saya mengubah `private CarServiceImpl carservice;` menjadi `private CarService carservice;` pada CarController. Hal ini sesuai dengan prinsip DIP dimana suatu class harus bergantung pada interface atau abstract class dibandingkan implementasi konkret.

2. The advantages of applying SOLID principles

    - Kode lebih mudah dipahami
    - Karena kode mudah dipahami, saya juga akan lebih mudah melakukan perubahan terhadap kode
    - Prinsip seperti SRP membuat lebih mudah ketika ingin melakukan testing karena kompleksitas yang rendah dari setiap method
    - Mengurangi resiko terjadinya bug. Dengan meimplemntasikan SOLID principle, project saya akan menjadi low coupling dan high cohesion. Hal ini akan membuat perubahan yang saya lakukan di suatu kode tidak akan mempengaruhi kode lainnya sehingga mengurangi terjadinya error. Sebagai contoh, sebelumnya jika saya ingin mengubah hal-hal yang berhubungan dengan Product atau Car, saya harus melakukannya di satu file saja, yaitu ProductController. Namun, sekarang saya hanya perlu mengubah CarController jika saya ingin mengubah hal-hal yang berhubungan dengan Car.

3. The disadvantages of not applying SOLID principles
    - Kode lebih sulit dipahami
    - Ribet ketika ingin menambahkan fungsionalitas baru
    - Testing lebih sulit dibuat
    - Error atau bug akan lebih mudah muncul
    - Contoh: Ketika ProductController dan CarController masih menjadi satu file, kita akan lebih sulit mencari dimana class CarController berada. Jika semua controller digabung dalam satu file, semakin banyak controller maka akan semakin banyak juga jumlah baris kode. Hal ini akan membuat penambahan controller baru semakin sulit. Resiko terjadinya error juga semakin besar karena bisa saja perubahan yang kita lakukan pada suatu method ternyata berpengaruh terhadap method di class lain.

</details>

## Module 4

<details>
<summary>Reflection</summary>

1. Ya, TDD flow ini membantu saya dengan membuat alur pengembangan program menjadi lebih terarah. Meski begitu, saya masih mengalami banyak kesulitan ketika membuat test, terkhususnya ketika berurusan dengan `Mock`. Saya rasa perlu latihan yang banyak untuk bisa menguasai TDD ini.

2.  - Fast: Ya, test saya sudah memenuhi prinsip ini. Saya sudah memisahkan antara unitTest dan functionalTest. Saya juga sudah mengimplementasikan `stubs`untuk _predetermined_ hasil dari suatu pemanggilan fungsi sehingga test tidak akan bergantung terhadap test lain.
    - Isolated: Ya, test saya sudah memenuhi prinsip ini. Saya menggunakan `mock objects` untuk digunakan di test saya. Saya juga menggunakan `setUp` untuk mencegah terjadinya duplikasi object.
    - Repeatable: Ya, test saya sudah memenuhi prinsip ini. Test saya sudah memenuhi prinsip Isolated sehingga setiap data yang diperlukan di dalam test akan tetap sama setiap kali testing dilakukan.
    - Self-Validating: Test saya belum sepenuhnya memenuhi prinsip ini. Test saya hanya menggunakan `assert` untuk validasi hasil. Meski begitu, masih banyak test yang memiliki banyak assert di dalamnya. Berikutnya, saya harus lebih memisahkan lagi setiap assert ke test yang berbeda.
    - Thorough: Ya, test saya sudah memenuhi prinsip ini. Test saya sudah mencakup semua _unhappy_ dan _happy paths_. Test saya juga sudah mencakup semua kemungkinan _error_.

</details>
