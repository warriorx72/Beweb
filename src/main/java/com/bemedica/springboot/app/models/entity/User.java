package com.bemedica.springboot.app.models.entity;


import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "web_user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long UserId;
	
	@Column(name="paciente_id")
	@NotBlank
	private Long paciente_id;
	
	@Column(name="user_email")
	@NotBlank
	private String userEmail;
	
	@Column(name="user_nombre")
	@NotBlank
	private String UserNombre;
	
	@Column(name="user_first_name")
	@NotBlank
	private String UserFirstName;
	
	@Column(name="user_last_name")
	@NotBlank
	private String UserLastName;
	
	@Column(name="user_name")
	@NotBlank
	private String userName;
	
	@Column(name="user_password")
	@NotBlank
	private String UserPassword;
	
	@Column(name="Tipo")
	@NotBlank
	private String Tipo;
	
	@Column(name="user_status")
	@NotBlank
	private String UserStatus;
	
	@ManyToMany(fetch = FetchType.LAZY) //campo que une dos tablas por medio de los ID
	@JoinTable(name="web_user_roles"
		,joinColumns=@JoinColumn(name="user_id")
		,inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getUserId() {
		return UserId;
	}

	public void setUserId(Long UserId) {
		this.UserId = UserId;
	}

	public Long getUserTipoId() {
		return paciente_id;
	}

	public void setUserTipoId(Long UserTipoId) {
		this.paciente_id = UserTipoId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String UserEmail) {
		this.userEmail = UserEmail;
	}

	public String getUserNombre() {
		return UserNombre;
	}

	public void setUserNombre(String UserNombre) {
		this.UserNombre = UserNombre;
	}

	public String getUserFirstName() {
		return UserFirstName;
	}

	public void setUserFirstName(String UserFirstName) {
		this.UserFirstName = UserFirstName;
	}

	public String getUserLastName() {
		return UserLastName;
	}

	public void setUserLastName(String UserLastName) {
		this.UserLastName = UserLastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String UserPassword) {
		this.UserPassword = UserPassword;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String Tipo) {
		this.Tipo = Tipo;
	}

	public String getUserStatus() {
		return UserStatus;
	}

	public void setUserStatus(String UserStatus) {
		this.UserStatus = UserStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		
		
		result = prime * result + ((UserId == null)? 0 : UserId.hashCode());
		result = prime * result + ((UserNombre == null)? 0 : UserNombre.hashCode());
		result = prime * result + ((paciente_id == null)? 0 : paciente_id.hashCode());
		result = prime * result + ((userName == null)? 0 : userName.hashCode());
		result = prime * result + ((UserPassword == null)? 0 :UserPassword.hashCode());
		result = prime * result + ((Tipo == null)? 0 : Tipo.hashCode());
		result = prime * result + ((userEmail == null)? 0 : userEmail.hashCode());
		result = prime * result + ((UserFirstName == null)? 0 : UserFirstName.hashCode());
		result = prime * result + ((UserLastName == null)? 0 : UserLastName.hashCode());
		result = prime * result + ((UserStatus == null)? 0 : UserStatus.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (UserNombre == null){
			if (other.UserNombre != null)
			return false;
		} else if (!UserNombre.equals(other.UserNombre))
			return false;
		if (userEmail == null){
			if (other.userEmail != null)
			return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (UserFirstName == null){
			if (other.UserFirstName != null)
			return false;
		} else if (!UserFirstName.equals(other.UserFirstName))
			return false;
		if (UserLastName == null){
			if (other.UserLastName != null)
			return false;
		} else if (!UserLastName.equals(other.UserLastName))
			return false;
		if (UserStatus == null){
			if (other.UserStatus != null)
			return false;
		} else if (!UserStatus.equals(other.UserStatus))
			return false;
		if (UserId == null){
			if (other.UserId != null)
			return false;
		} else if (!UserId.equals(other.UserId))
			return false;
		if (paciente_id == null){
			if (other.paciente_id != null)
			return false;
		} else if (!paciente_id.equals(other.paciente_id))
			return  false;
		if (userName == null){
			if (other.userName != null)
			return false;
		} else if (!userName.equals(other.userName))
			return  false;
		if (UserPassword == null){
			if (other.UserPassword != null)
			return false;
		} else if (!UserPassword.equals(other.UserPassword))
			return  false;
		if (Tipo == null){
			if (other.Tipo != null)
			return false;
		} else if (!Tipo.equals(other.Tipo))
			return  false;
		return true;
	}


	
}
