package br.com.carstore.service;

import br.com.carstore.dto.CarDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService{

    private List<CarDTO> cars;

    public CarServiceImpl(){
       cars = new ArrayList<CarDTO>();
    }

    @Override
    public List<CarDTO> findAll() {
        return cars;
    }

    @Override
    public void save(CarDTO carDTO) {
        // Valida null e vazio, pois coloquei um input hidden no index.html em form.
        if (carDTO.getId() == null || carDTO.getId().isEmpty()) {

            carDTO.setId(UUID.randomUUID().toString());
        }
        this.cars.add(carDTO);
    }

    @Override
    public void deleteById(String id) {

        this.cars.removeIf(car -> car.getId().equals(id));
    }

    @Override
    public void update(String id, CarDTO carDTO) {

        this.cars.replaceAll(car -> car.getId().equals(id) ? carDTO : car);

    }

    // Percorre a lista em memória procurando o Id correspondente, se não achar devolve null.

    @Override
    public CarDTO findById(String id) {
        return this.cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
