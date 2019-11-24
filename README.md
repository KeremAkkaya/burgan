# XML to JSON Converter
Merhaba bu projede yoğun olarak design patternlar yerine göre kullanılmış ve uygulanmıştır. Proje bir maven web architecture style ile oluşturulmuştur. Projede kullanılan yazılım dili JAVA dır.
XML ve JSON parse ve validasyon işlemleri için jackson kütüphaneleri kullanılmıştır.

## Çalıştırma Detayları
Uygulama 3 farklı jar ile çalışacak şekilde export edilmiştir. Aşağıda uygulamayı düzgün bir şekilde çalıştırabilmek için bazı komutlara yer verilmiştir.

### Server Uygulaması
jarların olduğu lokasyonda basitçe aşağıdaki komut çalıştırılır. 9091 portunu dinleyen basit bir server sockettir.

`java -jar server.jar`

### Client Uygulaması
Bu uygulama 9091 portunu dinleyen servera istenilen sayıda ve nitelikte request gönderilmesini sağlar. Örneğin hazır trace XML inden 35 adet istek göndermek için aşağıdaki komut çalıştırılabilir. 

`java -jar client.jar t 35``

'i' Information için 't' Trace hazır xml leri için parametriktir. 2. parametre istek sayısını belirtir.

### Batch
Bu uygulama da tüm requestleri 100 erli parçalara ayırarak dosyaların bulunduğu lokasyonda /logs klasörüne kaydeder. Uygulamayı çalıştırmak için aşağıdaki komut kullanılabilir.

`java -jar batch.jar`

Uygulama çalışırken console a stop yazılıp entera basıldığında uygulama işlemleri kesip kapanır. Uygulama tekrar çalıştırıldığında kaldığı yerden devam ederek çalışmasını sürdürür.


## Kullanılan Design Patternlar
### Abstract Factory
Bu pattern command pattern command objelerini oluşturmak amacıyla kullanılmıştır. Aslında command pattern a ait abstract factory design patternıdır.
### Factory
Bu pattern birkaç yerde polymorphisim yapmak amacıyla ve objeleri soyutlamak amacıyla kullanılmıştır. Örneğin `RequestFactory` Json veya Xml request objelerini oluşturmak amacıyla kullanılmıştır.
### Adapter
Bu pattern `IRequest` objeleri arasında dönüşüm yapmak amacıyla kullanılmıştır. `IWebRequestAdapter` interface üzerinden incelenebilir.
### Strategy
Bu pattern batch uygulamanın dosyaları nasıl işleyeceğini belirtir. Sequential veya multithreaded olarak dosyalar işlenebilir bunu yönetmek için strategy pattern kullanılmıştır.
### Observer
Observer pattern batch uygulamada ana thread üzerinden batch işleminin kesilmesini yönetmek için kullanılmıştır. Obje zinciri üzerinde kesilmeyi ilgilendiren objeler observer olarak kullanılmıştır.
### Command
Command pattern işlem basamaklarında hangi adımda nasıl bir aksiyon işletileceğini yönetmek için kullanılmıştır. Örneğin bir file üzerinde işlem tamamlandığında FTP ye yazılabilir veya tüm dosyaların işlenmesi tamamlandığında SMS veya Email gönderilebilir.
Tüm commandlar bir liste üzerinde tutulur ve istenilen noktalarda işlenmesi sağlanabilir.

## Varsayımlar
Uygulama dosya sistemi üzerinde kalınan noktaları tutmak için ".meta" dosyalarında bilgileri tutar.
Uygulama çalışması sırasında manuel düzenlemeler uygulamada tutarsızlıklara yol açabilir.

## Öneriler
Requestlerin işlenmesi için chain of responsibility pattern kullanılabilir. 
 
