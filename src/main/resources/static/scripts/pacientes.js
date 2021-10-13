window.addEventListener('load', (e) => {
    let botonListado = document.querySelector("#listado-pac"),
        botonNuevoPac = document.querySelector("#nuevo-pac"),
        formBuscar = document.querySelector("#formBuscar"),
        inputDni = document.querySelector("#buscarDni"),
        resultado = document.querySelector("#resultado"),
        listadoPac = document.querySelector(".listado"),
        sectionRegistro = document.querySelector(".datos");

    //buscar todos los pacientes
    botonListado.addEventListener('click', (e) => {
        
        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoPac.innerHTML = "";

        fetch(`http://localhost:8080/pacientes`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            
            sectionRegistro.innerHTML = `<h3>Nuestros Pacientes</h3>
                                            <ul class="paciente" id="lista-pac"></ul>`;
            let ulPac = document.querySelector("#lista-pac");
            info.map((pac) => {
                ulPac.innerHTML += `<li class="item">${pac.nombre} ${pac.apellido}, DNI ${pac.dni}, Fecha de ingreso: ${pac.fechaIngreso.split('-').reverse().join('/')}, Domicilio: ${pac.domicilio.calle} ${pac.domicilio.numero},  ${pac.domicilio.localidad},  ${pac.domicilio.provincia}</li>`
                })
        })
        .catch((error) => console.log(error))
    })


    //registrar nuevo paciente
    botonNuevoPac.addEventListener('click', (e) => {
        listadoPac.innerHTML = "";
        resultado.innerHTML = "";
        sectionRegistro.innerHTML = `<form id="datosPaciente">
                                        <label>Nombre</label>
                                        <input type="text" id="nombre" />
                                        <label>Apellido</label>
                                        <input type="text" id="apellido" />
                                        <label>DNI</label>
                                        <input type="text" id="dni" />
                                        <br>
                                        <br>
                                        <label>Domicilio:</label>
                                        <br>
                                        <label>Calle</label>
                                        <input type="text" id="calle" />
                                        <label>Número</label>
                                        <input type="text" id="numero" />
                                        <label>Localidad</label>
                                        <input type="text" id="localidad" />
                                        <label>Provincia</label>
                                        <input type="text" id="provincia" />
                                        <button type="submit" class="alterar">Guardar</button>
                                    </form>
                                    `
            
        let nombrePac = document.querySelector("#nombre"),
            apellidoPac = document.querySelector("#apellido"),
            dni = document.querySelector("#dni"),
            calle = document.querySelector("#calle"),
            numero = document.querySelector("#numero"),
            localidad = document.querySelector("#localidad"),
            provincia = document.querySelector("#provincia"),
            formulario = document.querySelector("#datosPaciente");
            
        formulario.addEventListener('submit', (e) => {
            e.preventDefault();
    
            let datosPaciente = {
                nombre : nombrePac.value,
                apellido : apellidoPac.value,
                dni : dni.value,
                domicilio : {
                    calle : calle.value,
                    numero : numero.value,
                    localidad : localidad.value,
                    provincia : provincia.value
                } 
            }
        
            let config = {
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body : JSON.stringify(datosPaciente)
            }
    
            fetch(`http://localhost:8080/pacientes/registro`, config) 
            .then((respuesta) => respuesta.json())
            .then((info) => {
                
                sectionRegistro.innerHTML = "";
                resultado.innerHTML = `<div class="exito">
                                            <h4>Guardado con exito!</h4>
                                            <p>Paciente: ${info.nombre} ${info.apellido}</p>
                                            <p>Dni: ${info.dni}</p>
                                        </div>`
                formulario.reset();
            })
            .catch((error) => console.log(error))
        })
    })


    //buscar paciente por dni --> modificar/eliminar
    formBuscar.addEventListener('submit', (e) => {
        e.preventDefault();

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoPac.innerHTML = "";

        fetch(`http://localhost:8080/pacientes/${inputDni.value}`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            listadoPac.innerHTML = `<h3>Nuestros Pacientes</h3>
                                     <ul class="paciente" id="lista-pac">
                                        <li>${info.nombre} ${info.apellido}, DNI ${info.dni}</li>
                                     </ul>
                                     <button type="submit" id="idModificar" class="alterar">Modificar</button>
                                     <button type="submit" id="idEliminar" class="alterar">Eliminar</button>
                                     `
            formBuscar.reset();

            //modificar el paciente buscado
            let botonModificar = document.querySelector("#idModificar");

            botonModificar.addEventListener('click', (e) => {

                listadoPac.innerHTML = "";
                sectionRegistro.innerHTML = `<form id="modifPaciente">
                                                <label>Nombre</label>
                                                <input type="text" id="nombre" />
                                                <label>Apellido</label>
                                                <input type="text" id="apellido" />
                                                <label>DNI</label>
                                                <input type="text" id="dni" />
                                                <br>
                                                <label>Domicilio:</label>
                                                <br>
                                                <br>
                                                <label>Calle</label>
                                                <input type="text" id="calle" />
                                                <label>Número</label>
                                                <input type="text" id="numero" />
                                                <label>Localidad</label>
                                                <input type="text" id="localidad" />
                                                <label>Provincia</label>
                                                <input type="text" id="provincia" />                                              
                                                <button type="submit" class="alterar">Guardar</button>
                                             </form>`
                                             
                document.querySelector("#nombre").value = info.nombre;
                document.querySelector("#apellido").value = info.apellido;
                document.querySelector("#dni").value = info.dni;
                document.querySelector("#calle").value = info.domicilio.calle;
                document.querySelector("#numero").value = info.domicilio.numero;
                document.querySelector("#localidad").value = info.domicilio.localidad;
                document.querySelector("#provincia").value = info.domicilio.provincia;

                let formModificar = document.querySelector("#modifPaciente");
                formModificar.addEventListener('submit', (e) => {
                    e.preventDefault();

                    let datosPaciente = {
                        id : info.id,
                        nombre : document.querySelector("#nombre").value,
                        apellido : document.querySelector("#apellido").value,
                        dni : document.querySelector("#dni").value,
                        fechaIngreso : info.fechaIngreso,
                        domicilio : {
                            calle : document.querySelector("#calle").value,
                            numero : document.querySelector("#numero").value,
                            localidad : document.querySelector("#localidad").value,
                            provincia : document.querySelector("#provincia").value
                        }  
                    }
            
                    let config = {
                        method : "PUT",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body : JSON.stringify(datosPaciente)
                    }

                    fetch(`http://localhost:8080/pacientes/modificar`, config) 
                    .then((respuesta) => respuesta.json())
                    .then((info) => {
                        listadoPac.innerHTML = "";
                        sectionRegistro.innerHTML = "";
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Guardado con exito!</h4>
                                                    <p>Odontologo: ${info.nombre} ${info.apellido}</p>
                                                    <p>Dni: ${info.dni}</p>
                                                </div>`
                        formModificar.reset();
                    })
                    .catch((error) => console.log(error))

                })
            })


            //borrar el paciente buscado
            let botonBorrar = document.querySelector("#idEliminar");
            botonBorrar.addEventListener('click', (e) => {

                let config = {
                    method : "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body : ""
                }

                fetch(`http://localhost:8080/pacientes/borrar/${info.id}`, config)
                .then((respuesta) => {
                    if(respuesta.status === 204) {
                        listadoPac.innerHTML = "";
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Eliminado con exito!</h4>
                                                <div>`;
                        
                    }
                })
                .then((info) => console.log(info))
                .catch((error) => console.log(error))

            })


        })
        .catch((error) => console.log(error))

    })

})