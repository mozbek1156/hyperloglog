# Olasılıksal Veri Yapıları: HyperLogLog Teorik Analizi

## Küme Büyüklüğü Tahmini ve Harmonik Ortalama

HyperLogLog (HLL) algoritması, büyük veri kümelerindeki benzersiz eleman (cardinality) sayısını bellek açısından çok verimli bir şekilde tahmin etmek için kullanılır. Geleneksel yöntemler (örneğin HashSet) $O(N)$ bellek gerektirirken, HLL yalnızca $O(\log(\log(N)))$ bellek kullanır.

Algoritma, elemanları yüksek kaliteli bir karma işlevinden (Hash Function) geçirir. Oluşan bit dizisinin ilk $p$ biti, elemanın atanacağı kova (bucket) indeksini belirler. Diğer bitlerdeki "ardışık sıfır sayısı + 1" ($\rho$) hesaplanır ve bu değer ilgili kovanın register'ına (yazmacına) atanır.

Eğer kova sayısı $m = 2^p$ ise, her bir kova $N/m$ benzersiz elemanı işler. Maksimum ardışık sıfır sayısına göre, bir kovanın gözlemlediği benzersiz eleman sayısı istatistiksel olarak $2^{\rho}$ ile orantılıdır.

Farklı kovalardaki rastgeleliği dengelemek ve aykırı değerlerin (outliers) etkisini en aza indirmek için algoritmada **Harmonik Ortalama** (Harmonic Mean) kullanılır. Aritmetik ortalama, tesadüfen büyük $\rho$ değerine sahip birkaç kova tarafından ciddi şekilde bozulabilirken, harmonik ortalama daha dengeli bir tahmin sunar.

## Kova Sayısının ($m$) Tahmin Hatası Üzerindeki Etkisi

HLL algoritmasının standart tahmini (Harmonik Ortalama Formülü) şöyledir:
$$ E = \alpha_m \cdot m^2 \cdot \left( \sum_{j=1}^{m} 2^{-M[j]} \right)^{-1} $$

Burada:
- $m$: Kova sayısı ($2^p$)
- $M[j]$: $j$. kovadaki maksimum $\rho$ (sıfır sayısı + 1) değeri
- $\alpha_m$: Algoritmanın sapmasını (bias) gideren sabit düzeltme katsayısı (Örnek: $m \ge 128$ için $\alpha_m \approx 0.7213 / (1 + 1.079 / m)$)

Algoritmanın teorik tahmin hatası (Standard Error - SE), kova sayısı $m$ ile ters orantılıdır:
$$ SE \approx \frac{1.04}{\sqrt{m}} $$

### Matematiksel İnceleme

1. **Bağımsız Rastgele Değişkenler:** Her kova birbirinden bağımsız tahminler yürütür. $m$ adet bağımsız tahminin ortalaması alındığında, Merkezi Limit Teoremi'ne göre varyans (variance) $m$ ile ters orantılı olarak düşer. Standart sapma, varyansın karekökü olduğu için $\frac{1}{\sqrt{m}}$ şeklinde küçülür.
   
2. **Kova Sayısı ($m$) Arttıkça Hata Düşer:**
   - Eğer $p=4$ ($m=16$) seçerseniz, $SE \approx 1.04 / \sqrt{16} = 26\%$.
   - Eğer $p=10$ ($m=1024$) seçerseniz, $SE \approx 1.04 / \sqrt{1024} = 3.25\%$.
   - Eğer $p=14$ ($m=16384$) seçerseniz, $SE \approx 1.04 / \sqrt{16384} \approx 0.81\%$.

### Bellek vs Hassasiyet (Trade-off)

$m$ değerini artırmak tahmini daha kesin hale getirir, ancak bellek kullanımını da doğrusal olarak artırır. Standart bir HLL tasarımında her bir kova için 1 byte ($\rho$ değeri maksimum 64 olabileceği için 6 bit veya 1 byte yeterlidir) veya bazı implementasyonlarda daha az (5-6 bit) bellek ayrılır.
- $p=14$ için $16384 \times 1 \text{ byte} = 16 \text{ KB}$ bellek tüketilir. Bu 16 KB ile milyarlarca benzersiz öğe sadece %0.81 hata payı ile tahmin edilebilir.

Özetle, $m$ parametresinin artırılması, logaritmik olarak artan bellek tüketimi (çünkü $m = 2^p$) karşılığında tahminin güven aralığını daraltır ve hata oranını matematiksel formüle ($1.04 / \sqrt{m}$) uygun olarak stabilize eder.
