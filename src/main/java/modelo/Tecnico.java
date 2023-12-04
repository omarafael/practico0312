package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import DAO.IncidenteDAO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table (name="Tecnico")
@NoArgsConstructor
@Getter @Setter

	public class Tecnico implements Serializable{
	@Id
	@Column (name="DNI")
	private int DNI;
	@Column (name="nombre")
	private String nombre;
	@Column (name="apellido")
	private String apellido;
	@Column (name="telefono")
	private String telefono;
	@Column (name="mail")
	private String mail;
	@ManyToMany
	private List <Servicio> especialidades;
	@Column (name="activo")
	private boolean activo = true;
	@ManyToMany
	private List <TipoDeProblema> tiposDeProblemas;
	@Column (name="medioContacto")
	private String medioContacto;
	private LocalDate proximaFechaDisponible;
	
	
	//public Tecnico() {}
	
	public Tecnico(int DNI, String nombre, String apellido, String telefono, String mail,
	 String medioContacto) {
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.mail = mail;
		this.medioContacto = medioContacto;
		this.especialidades = new ArrayList<Servicio>();
		this.tiposDeProblemas = new ArrayList<TipoDeProblema>();
	}
	
	public void bajaTecnico() {
		this.activo = false;
	}
	
	public void agregarEspecialidad(Servicio servicio) {
		especialidades.add(servicio);
	}
	
	public void agregarTipoDeProblema(TipoDeProblema tipoDeProblema) {
		tiposDeProblemas.add(tipoDeProblema);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public List<Servicio> especialidades() {
		return especialidades;
	}
	
	public int getDNI() {
		return this.DNI;
	}
	
	public void setProximaFechaDisponible(LocalDate fecha) {
		this.proximaFechaDisponible = fecha;
	}
	
	public LocalDate getProximaFechaDisponible() {
		return this.proximaFechaDisponible;
	}
}
