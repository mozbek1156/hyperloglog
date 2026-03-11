# HyperLogLog Implementation

Bu proje, büyük veri kümelerindeki benzersiz eleman (cardinality) sayısını bellek açısından çok verimli bir şekilde tahmin etmek için kullanılan **HyperLogLog (HLL)** algoritmasının teorik analizini ve bir implementasyonunu içermektedir.

## İçerik

- `analysis.md`: HyperLogLog algoritmasının teorik temellerini, matematiksel incelemesini (Harmonik Ortalama, sapma düzeltmesi) ve kova sayısının ($m$) hata oranı ile bellek tüketimi üzerindeki etkisini detaylandıran analiz belgesi.
- `src/`: Java dilinde yazılmış HyperLogLog algoritması kaynak kodları.

## HyperLogLog Hakkında

Geleneksel yöntemler $O(N)$ bellek gerektirirken, HLL yalnızca $O(\log(\log(N)))$ bellek kullanarak benzersiz eleman sayısını yüksek bir doğrulukla tahmin edebilir. Logaritmik olarak artan bellek tüketimi karşılığında tahminin güven aralığını daraltır ve hata oranını matematiksel formüle uygun olarak minimize eder.

## Gereksinimler

- Java Development Kit (JDK) 8 veya üzeri

## Kullanım

Projeyi yerel ortamınıza klonladıktan veya indirdikten sonra uygun bir Java IDE'si (IntelliJ IDEA, Eclipse, vb.) ile açıp çalıştırabilirsiniz.
