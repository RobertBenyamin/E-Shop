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

- Kode saya masih belum mengimplementasikan error handling yang baik. Peningkatan yang bisa saya lakukan adalah dengan mengimplemntasikan error handling sesuai standar terutama pada fungsi-fungsi seperti create, edit, dan delete product.

- Cara saya menerapkan ID untuk product masih sangat tidak efektif yang berakibat ke algoritma untuk mencari product menggunakan ID juga tidak efektif. Peningkatan yang bisa saya lakukan adalah menggunakan ID generator kemudian menyimpan data product di database, bukan di ArrayList.

- Kode saya belum menerapkan validasi input. Peningkatan yang bisa saya lakukan adalah menerapkan validasi input ketika user create dan edit product.

</details>

<details>
<summary>Reflection 2</summary>

1.  - Untuk saat ini, saya belum terlalu merasakan suatu hal positif dari menulis unit test. Hal ini karena saya belum merasakan langsung manfaat dari adanya test tersebut. Namun, saya merasa kode saya akan lebih aman dari error ketika mengalami perubahan. 
    - Jumlah unit test yang perlu dibuat untuk satu class bergantung pada kompleksitas class tersebut. Semakin kompleks suatu class, maka semakin banyak unit test yang harus dibuat.
    - Suatu unit tests dikatakan cukup untuk memverifikasi suatu program ketika semua kemungkinan skenario dari program kita telah dihandle oleh suatu unit test.
    - 100% code coverage tidak menjamin bahwa kode kita bebas dari error atau bug. 100% code coverage hanya berarti seluruh bagian kode kita telah dites. Namun suatu bagian kode bisa memiliki beberapa skenario. Bisa saja terdapat kemungkinan skenario yang tidak ditangani oleh test kita.

2. Menurut saya kode tersebut jadi kurang bersih karena terdapat redundansi kode. Selain itu, jika suatu saat kita perlu melakukan perubahan pada setup prosedur, maka kita perlu melakukan dua kali perubahan. Untuk meningkatkan kode tersebut, kita dapat menempatkan test untuk menghitung jumlah item pada product list di file yang sama sehingga kita tidak perlu menjalankan prosedur setup dua kali.

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