<div align="center">

# 🚀 HyperLogLog Implementation

**Büyük veri kümelerindeki benzersiz eleman (cardinality) sayısını olağanüstü bellek verimliliği ile tahmin etmek için tasarlanmış yüksek hızlı yapı**

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

</div>

---

## 🔍 Proje Hakkında

**HyperLogLog (HLL)** algoritması, milyarlarca elemandan oluşan devasa veri yığınlarındaki "benzersiz (unique)" öğe sayısını tahmin etmek için kullanılan, Google ve modern veri tabanları tarafından (Redis, Presto vb.) sıklıkla tercih edilen **olasılıksal (probabilistic)** bir veri yapısıdır.

Geleneksel yöntemler (örneğin `HashSet`) her bir elemanı hafızada tuttuğu için **$O(N)$** bellek tüketirken, HyperLogLog algoritması **$O(\log(\log(N)))$** gibi mikroskobik bir bellek tüketimiyle harikalar yaratır!

### ✨ Öne Çıkan Özellikler

- **⚡ Maksimum Hız**: Karma (Hash) tabanlı kovalarla (buckets) $O(1)$ ekleme süresi.
- **💾 Minimum Bellek Tüketimi**: Milyarlarca veriyi sadece birkaç Kilobayt (KB) bellek kullanarak sayma yeteneği.
- **🎯 Yüksek Hassasiyet**: Matematiksel olarak kanıtlanmış "Harmonik Ortalama" tabanlı sapma düzeltmesi (Bias Correction).

---

## 📂 İçerik Mimarisi

- `📄 analysis.md`: HyperLogLog algoritmasının detaylı teorik temellerini, matematiksel incelemesini (Harmonik Ortalama, sapma düzeltmesi) ve kova sayısının ($m$) hata oranı ile bellek tüketimi üzerindeki etkisini anlatan kapsamlı döküman.
- `💻 src/`: Java programlama dilinde sıfırdan yazılmış, temiz ve anlaşılır HyperLogLog algoritması kaynak kodları.

---

## 🔬 Nasıl Çalışır? (Matematiksel Kısa Bakış)

HLL algoritması, elemanları önce güvenilir bir Hash fonksiyonundan geçirir. 
Elde edilen bit diziliminde:
1. İlk $p$ adet bit, elemanın **hangi kovaya (bucket)** atanacağını belirler.
2. Geriye kalan bitlerdeki **"ardışık sıfır sayısı + 1 ($\rho$)"** hesaplanır ve o kovanın kaydında (register) tutulur.

Algoritmanın standart tahmin formülü (Harmonik Ortalama):
$$ E = \alpha_m \cdot m^2 \cdot \left( \sum_{j=1}^{m} 2^{-M[j]} \right)^{-1} $$

> *"Bellek tüketimi ile doğruluk oranı arasında mükemmel bir denge kurulur. Kova sayısı $m$ arttıkça doğruluk artar, ancak bellek gereksinimi logaritmik olarak genişler."*

---

## ⚙️ Kurulum & Kullanım

Bu projeyi yerel makinenize kurup hemen test etmeye başlayabilirsiniz:

### 1️⃣ Repoyu Klonlayın
```bash
git clone https://github.com/mozbek1156/hyperloglog.git
cd hyperloglog
```

### 2️⃣ Ortam Gereksinimleri
- **Java Development Kit (JDK) 8+**
- Herhangi bir modern Java IDE'si (IntelliJ IDEA, Eclipse, VSCode vb.)

### 3️⃣ Çalıştırma
Projeyi favori IDE'niz ile açarak `src` klasörü altındaki ana (main) sınıf üzerinden yapılandırmayı veya demo kodlarını doğrudan çalıştırabilirsiniz.

---

<div align="center">
Geliştirici: <b>Muhammed Özbek</b> • Olasılıksal algoritmalar dünyasına hoş geldiniz!
</div>
