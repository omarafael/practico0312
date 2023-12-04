package modelo;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import DAO.IncidenteDAO;
import DAO.ServicioDAO;
import DAO.TecnicoDAO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name="incidente")
@NoArgsConstructor
@Getter @Setter
public class Incidente implements Serializable {
	@Id
	@Column	(name="idIncidente")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idIncidente;
	//@ManyToOne	
	//private Cliente cliente;
	//@ManyToOne	
	//private Servicio servicio;
	@ManyToOne
	private ProvisionDeServicio provisionDeServicio;
	@ManyToMany
	private List<TipoDeProblema> tiposDeProblemas;
	@Column	(name="descripcionDeProblema")
	private String descripcionDeProblema;
	@ManyToOne
	private Tecnico tecnicoAsignado;
	@Column	(name="hsAdicionalesComplejo")
	private int hsAdicionalesComplejo;
	@Column	(name="fechaEstimadaDeResolucion")
	private LocalDate fechaEstimadaDeResolucion;
	@Column	(name="fechaIncidente")
	private LocalDate fechaIncidente;
	@Column	(name="fechaFinIncidente")
	private Date fechaFinIncidente;
	@Column	(name="activo")
	private boolean activo=true;
	@Column	(name="estado")
	private Estado estado = Estado.INICIADO;
	
	//public Incidente() {}
	
	public Incidente(String descripcionDeProblema,LocalDate fechaIncidente) {
		
		//this.cliente = cliente;
		//this.servicio = servicio;
		this.descripcionDeProblema = descripcionDeProblema;
		this.fechaIncidente = fechaIncidente;
		tiposDeProblemas = new ArrayList<TipoDeProblema>();
	}
	
	public void bajaIncidente() {
		this.activo = false;
	}
	
	public void agregarTipoDeProblema(TipoDeProblema tipoDeProblema) {
		tiposDeProblemas.add(tipoDeProblema);
	}
	 
	public List tiposDeProblemas () {
		return tiposDeProblemas;
	}
	
	public void setTecnico (Tecnico tecnico) {
		this.tecnicoAsignado = tecnico;
	}
	
	public Tecnico getTecnicoAsignado() {
		return this.tecnicoAsignado;
	}
	
	public LocalDate getFechaEstimadaDeResolucion() {
		return this.fechaEstimadaDeResolucion;
	}
	
	public void setFechaEstimadaDeResolucion(LocalDate fecha) {
		this.fechaEstimadaDeResolucion = fecha;
	}
	
	public List buscarTecnicosDisponibles(Servicio especialidad) {
		
		TecnicoDAO tecnicoDAO = new TecnicoDAO();
			
		List<Tecnico> tecnicosDisponibles = tecnicoDAO.findAll().stream()
                .filter(tecnico -> tecnico.especialidades().contains(especialidad))
                .collect(Collectors.toList());
		
		tecnicosDisponibles.forEach(tecnico ->buscarUltimaFechaEstimadaResolucion(tecnico));
		
		tecnicosDisponibles.forEach(tecnico -> System.out.println(tecnico.getNombre() + " " + tecnico.getProximaFechaDisponible()));
			return tecnicosDisponibles;	
	}
	
	
	
		public void buscarUltimaFechaEstimadaResolucion(Tecnico tecnico) {
		
		IncidenteDAO incidenteDAO = new IncidenteDAO();
		List<Incidente> incidentesDelTecnico =  incidenteDAO.findAll().stream()
				.filter(incidente -> incidente.getTecnicoAsignado().getDNI()==tecnico.getDNI())
				.collect(Collectors.toList());
		
		LocalDate ultimaFechaEstimada = Collections.max(incidentesDelTecnico, 
				Comparator.comparing(Incidente::getFechaEstimadaDeResolucion))
				.getFechaEstimadaDeResolucion();
		
		tecnico.setProximaFechaDisponible(ultimaFechaEstimada);
		
		
		}	
	

}