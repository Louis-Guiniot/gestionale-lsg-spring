package it.gestionalejaclsg.jac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bodyinvoice")
@Data
public class BodyInvoice {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_Invoice")
	private int idInvoice;
	
	@Column(name="codice_articolo")
	private String codiceArticolo;
	
	@Column(name="descrizione")
	private String descrizione;
	
	@Column(name="unitamisura")
	private String unitamisura;
	
	@Column(name="quantita")
	private String quantita;
	
	@Column(name="importo_unitario")
	private String importoUnitario;

	@Column(name="sconto_formula")
	private String scontoFormula;

	@Column(name="prezzo_netto")
	private String prezzoNetto;	
	
	@Column(name="importo_sconto")
	private String importoSconto;
	
	@Column(name="imponibile")
	private String imponibile;
	
	@Column(name="iva")
	private String iva;
	
 	@Column(name = "imposta")
	private String imposta;

	@Column(name="totale_righe")
	private String totaleRighe;

}
