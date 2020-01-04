package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, int months) {
			double basicQuota = contract.getTotalValue()/ months;
			// Pega o valor basico = total / meses.
			for(int i = 1; i <= months; i++) {
				Date date = addMonths(contract.getDate(), i);
				double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
				double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
				
				contract.addInstallment(new Installment(date, fullQuota));
			}
	}
			
			private Date addMonths(Date date, int n) {
				Calendar cal = Calendar.getInstance(); // Pega uma instância do calendário atual
				cal.setTime(date); // Joga na cal a data passada.
				cal.add(Calendar.MONTH, n);// adiciona n meses na variavel cal.
				// O que vai adiciona(dia, mes, ano) / Quantidade a ser adicionada.
				return cal.getTime();	//retorna a data.		
	}
}
