<div align="center">

# 🚀 Küme Büyüklüğü Tahmini (Cardinality Estimation): HyperLogLog Tasarımı

**Büyük Veri Analitiğinde Olasılıksal Veri Yapıları - Final Değerlendirme Ödevi 2**

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

</div>

---

## 📌 Proje Amacı ve Kapsamı

Bu proje, büyük veri kümelerindeki benzersiz eleman (cardinality) sayısını minimum bellek (memory) tüketimi ile tahmin etmeye yarayan **HyperLogLog (HLL)** olasılıksal veri yapısının sıfırdan tasarlanıp gerçeklenmesini (implementation) içermektedir. 

Geleneksel veri yapıları (örn. `HashSet`) **$O(N)$** bellek karmaşıklığına sahipken, HyperLogLog algoritması **$O(\log(\log(N)))$** bellek tüketimi ile çalışarak milyarlarca veriyi yalnızca birkaç kilobaytlık bir alanda çok düşük bir hata payıyla sayabilmektedir.

---

## ⚙️ Gerçeklenen Temel Bileşenler (Özellikler)

Proje, HLL algoritmasının teorik temellerine tamamen sadık kalınarak geliştirilmiş olup aşağıdaki kritik bileşenleri barındırmaktadır:

1. **Yüksek Kaliteli Hash Fonksiyonu:** Verilerin rastgele ve homojen bir şekilde dağıtılmasını sağlayarak varyansı (variance) azaltan karma mekanizması.
2. **Kovalama (Bucketing) Mekanizması:** Girdi verisinin alt kümelere ayrılarak bağımsız tahminler oluşturulmasını sağlayan bit tabanlı kova (bucket) yönlendirmesi.
3. **Register (Yazmaç) Takibi:** Her kovada gözlemlenen en uzun "ardışık sıfır sayısını" ($\rho$) tutan ve bellek verimliliğini maksimize eden durum tabloları.
4. **Harmonik Ortalama (Harmonic Mean) Formülü:** Kovalar arasındaki uç değerlerin (outliers) etkisini sönümleyerek nihai tahminin kalitesini artıran matematiksel ortalama yöntemi.
5. **Düzeltme Faktörleri (Bias Corrections):** Hem çok küçük (Small Range) hem de çok büyük (Large Range) veri setlerinde oluşabilecek sapmaları önlemek adına literatürdeki standart HLL düzeltme katsayılarının koda entegrasyonu.
6. **Birleştirilebilirlik (Mergeability):** Dağıtık veri tabanlarında sıkça ihtiyaç duyulan, iki farklı HLL yapısının veri kaybı olmaksızın ($O(m)$ sürede) tek bir HLL altında birleştirilebilme özelliği.

---

## 📊 Teorik Algoritma Analizi

Kova sayısının ($m$) tahmini hata oranı (Standard Error) üzerindeki matematiksel etkisi `analysis.md` (veya sunum dokümanı) dosyasında detaylandırılmıştır. 

Temel kural olarak, standart hata formülü şu şekildedir:
$$ SE \approx \frac{1.04}{\sqrt{m}} $$

Kova sayısı $m$ ($2^p$) logaritmik olarak artırıldığında, bellek tüketimi doğrusal olarak büyürken, tahmin hatası parabolik olarak küçülmektedir. Tüm detaylı incelemeler, matematiksel ispatlar ve örneklemeler analiz dokümanında ve proje sunumunda görülebilir.

---

## 📁 Dosya Yapısı

- `📄 README.md`: Proje genel bakışı ve özellikleri (Bu dosya).
- `📄 analysis.md`: Algoritmanın teorik ve matematiksel incelemelerinin yer aldığı detaylı analiz kılavuzu.
- `📂 src/`: HLL bileşenlerinin sıfırdan implemente edildiği tüm kaynak kodları (Hashleme, Kova mekanizması, Merge işlemi vb.).
- `📄 sunum_dokumani.pdf`: (veya `pptx`) Algoritma analizi ve teknik detayların bulunduğu ödev sunum materyali.

---

## 🚀 Kurulum ve Çalıştırma (Çalıştırma Kanıtı)

### Klonlama
```bash
git clone https://github.com/mozbek1156/hyperloglog.git
cd hyperloglog
```

### Kodun Çalıştırılması
Java derleyicisi veya bir IDE (Run tuşu) vasıtasıyla `src/` klasörü içerisindeki ana `Main` (veya Demo) sınıfı çalıştırılabilir. Çalıştığınızda ekranda oluşturulan elemanların gerçek sayısı ve HyperLogLog'un tahmini arasındaki küçük hata payını gösteren teknik log çıktısı alınacaktır. Ayrıntılı çalıştırma görüntüleri proje sunum videosunda mevcuttur.

---
<div align="center">
Geliştirici: <b>Muhammed Özbek</b><br>

</div>
