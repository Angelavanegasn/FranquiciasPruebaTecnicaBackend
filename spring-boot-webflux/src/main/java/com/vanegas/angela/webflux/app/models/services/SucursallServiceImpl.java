package com.vanegas.angela.webflux.app.models.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanegas.angela.webflux.app.models.dao.FranquiciaDao;
import com.vanegas.angela.webflux.app.models.dao.SucursalDao;
import com.vanegas.angela.webflux.app.models.documents.Franquicia;
import com.vanegas.angela.webflux.app.models.documents.Sucursal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SucursallServiceImpl implements SucursalService{

	@Autowired
	private SucursalDao sucursalDao;
	
	@Autowired
	private FranquiciaDao franquiciaDao;
	
	

	@Override
	public Mono<Sucursal> findSucursalById(String id) {
		
		return sucursalDao.findById(id);
	}

	@Override
	public Mono<Franquicia> saveFranquicia(Franquicia sucursal) {
		return franquiciaDao.save(sucursal);
	}

	public Flux<Franquicia> findAllFranquicias(){
		return franquiciaDao.findAll();
	}

	@Override
	public Flux<Sucursal> findAllSucursales() {
		
		return sucursalDao.findAll();
	}

	@Override
	public Mono<Sucursal> saveSucursal(Sucursal sucursal) {
		if(sucursal.getCreateAt() == null) {
			sucursal.setCreateAt(new Date());
		}
		return sucursalDao.save(sucursal);
	}


	@Override
	public Mono<Sucursal> findByNombre(String nombre) {
		return sucursalDao.findByNombre(nombre);
	}


}
