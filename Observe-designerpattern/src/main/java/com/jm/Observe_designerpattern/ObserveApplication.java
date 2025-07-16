package com.jm.Observe_designerpattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

// Sistema de gerenciamento de notificacoes de bolsa de valores - ao usuario ( funcoes lambda)
// desacoplamento de (classe/funcoes lambdas) diferentes -  para escalabilidade
// flexibilidade add e remove - em tempo de execução
// reusable por estarem em componentes diferentes (funcoes lambdas ou classes)

@SpringBootApplication
public class ObserveApplication {

	public static void main(String[] args) {

		var stockData = new StockData();

		stockData.addObserver((stockName, stockPrice) -> System.out
				.println("Console Notification - Stock: "+ stockName + " Price" + stockPrice));

		stockData.addObserver((stockName, stockPrice) -> System.out
				.println("Email Notification - Stock: "+ stockName + "Price" + stockPrice));

		stockData.addObserver(NotificationMethods :: smsNotification);

		stockData.newStockData("TESLA",150.0);
		stockData.newStockData("AMAZON",2500.0);


	}
}

class NotificationMethods{
	public static void smsNotification(String stockName, double stockPrice) {
		System.out.println("SMS Notification - Stock: "+ stockName +"Price" + stockPrice);

	}

}

class StockData{
	private final List<BiConsumer<String, Double>> observers = new ArrayList<>();

	public void addObserver(BiConsumer<String, Double> observer) {
		observers.add(observer);
	}

	public void removeObserver(BiConsumer<String, Double> observer){
		observers.remove(observer);
	}

	public void newStockData(String stockName, Double stockPrice){
		observers.forEach(observer -> observer.accept(stockName, stockPrice));
	}

}
