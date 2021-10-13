window.addEventListener('load', (e) => {

    let botonListado = document.querySelector("#listado-turnos"),
        botonSemanales = document.querySelector("#ts"),
        botonNuevoTurno = document.querySelector("#nuevo-turno"),
        formBuscar = document.querySelector("#form_buscar"),
        inputDni = document.querySelector("#dni_buscar"),
        resultado = document.querySelector("#resultado"),
        listadoTurnos = document.querySelector(".listado"),
        sectionRegistro = document.querySelector(".datos");

    //buscar todos los turnos
    botonListado.addEventListener('click', (e) => {

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoTurnos.innerHTML = "";

        fetch(`http://localhost:8080/turnos`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
           
            sectionRegistro.innerHTML =  `<h3>Nuestros Turnos</h3>
                                        <ul class="turno" id="lista-turnos"></ul>`;
            let ulTurnos = document.querySelector("#lista-turnos");
            info.map((turno) => {
                ulTurnos.innerHTML += `<li class="item">Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}, Fecha: ${turno.fechaHora.slice(0, 10).split('-').reverse().join('-')} Horario: ${turno.fechaHora.slice(11, 16)}hs Odontologo: ${turno.odontologo.nombre} ${turno.odontologo.apellido}</li>`
            })
        })
        .catch((error) => console.log(error))
    })


    //buscar turnos semanales
    botonSemanales.addEventListener('click', (e) => {
        
        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoTurnos.innerHTML = "";

        fetch(`http://localhost:8080/turnos/semana`)
        .then((respuesta) => respuesta.json())
        .then((info) => {

            sectionRegistro.innerHTML =  `<h3>Turnos Semanales</h3>
                                        <ul class="turno" id="lista-turnos"></ul>`;
            let ulTurnos = document.querySelector("#lista-turnos");
            info.map((turno) => {
                ulTurnos.innerHTML += `<li class="item">Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}, Fecha: ${turno.fechaHora.slice(0, 10).split('-').reverse().join('-')} Horario: ${turno.fechaHora.slice(11, 16)}hs Odontologo: ${turno.odontologo.nombre} ${turno.odontologo.apellido}</li>`
            })
        })
        .catch((error) => console.log(error))
    })
    
        

    //registrar nuevo turno
    botonNuevoTurno.addEventListener('click', (e) => {
        listadoTurnos.innerHTML = "";
        resultado.innerHTML = "";
        sectionRegistro.innerHTML = `<form id="datosTurno">
                                        <label>Fecha</label>
                                        <input type="date" id="fecha" />
                                        <label>Hora</label>
                                        <input type="time" id="hora" />
                                        <label>ID Paciente</label>
                                        <input type="text" id="id-pac" />
                                        <label>ID Odontologo</label>
                                        <input type="text" id="id-odon" />
                                        <button type="submit" class="alterar">Guardar</button>
                                    </form>
                                    `
        let fechaTurno = document.querySelector("#fecha"),
            horaTurno = document.querySelector("#hora"),
            idPaciente = document.querySelector("#id-pac"),
            idOdontologo = document.querySelector("#id-odon"),
            formulario = document.querySelector("#datosTurno");

            

        formulario.addEventListener('submit', (e) => {
            e.preventDefault();
                
            let datosTurno = {
                fechaHora : fechaTurno.value + " " + horaTurno.value,
                odontologo : {
                    id: idOdontologo.value
                },
                paciente : {
                    id: idPaciente.value
                }
            }
        
            let config = {
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body : JSON.stringify(datosTurno)
            }

            fetch(`http://localhost:8080/turnos/registro`, config) 
            .then((respuesta) => respuesta.json())
            .then((info) => {
               
                listadoTurnos.innerHTML = "";
                sectionRegistro.innerHTML = "";
                resultado.innerHTML = `<div class="exito">
                                            <h4>Guardado con exito!</h4>
                                            <p>Fecha: ${info.fechaHora.slice(0, 10).split('-').reverse().join('-')} Horario: ${info.fechaHora.slice(11, 16)}hs</p>
                                        </div>`
                formulario.reset();
            })
            .catch((error) => console.log(error))
        })

    })


    //buscar turno por dni --> modificar/eliminar
    formBuscar.addEventListener('submit', (e) => {
        e.preventDefault();

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoTurnos.innerHTML = "";
        fetch(`http://localhost:8080/turnos/pac/${inputDni.value}`)
        .then((response) => response.json())
        .then((info) => {

            listadoTurnos.innerHTML = `<h3>Nuestros Turnos</h3>
                                        <ul class="turno" id="lista-turnos">
                                            
                                        </ul>
                                        `
                            
            let ulTurnos = document.querySelector("#lista-turnos");
            info.map((turno) => {
                ulTurnos.innerHTML +=
                `
                <li data-turno="${turno.id}">Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}, Fecha: ${turno.fechaHora.slice(0, 10).split('-').reverse().join('-')} Horario: ${turno.fechaHora.slice(11, 16)}hs Odontologo: ${turno.odontologo.nombre} ${turno.odontologo.apellido}
                   
                    <button type="submit" class="modificar alterar">Modificar</button>
                    <button type="submit" class="eliminar alterar">Eliminar</button>
                
                </li>
                
                `
            })
                
            formBuscar.reset();

            //modificar el turno buscado
            let botonesModificar = document.querySelectorAll(".modificar");
            botonesModificar.forEach((boton) => {
                boton.addEventListener('click', (e) => {
                    let liParent = boton.parentNode;
                    
                    sectionRegistro.innerHTML = `<form id="modifTurno">
                                                    <label>Fecha</label>
                                                    <input type="date" id="fecha"/>
                                                    <label>Hora</label>
                                                    <input type="time" id="hora"/>
                                                    <label>ID Paciente</label>
                                                    <input type="text" id="id-pac" />
                                                    <label>ID Odontologo</label>
                                                    <input type="text" id="id-odon" />
                                                    <button type="submit" class="alterar">Guardar</button>
                                                </form>`
                          
                                                
                    document.querySelector("#fecha").value = info[liParent.dataset.turno - 1].fechaHora.slice(0, 10).split('-').reverse().join('-');
                    document.querySelector("#hora").value = info[liParent.dataset.turno - 1].fechaHora.slice(11, 16);
                    document.querySelector("#id-pac").value = info[liParent.dataset.turno - 1].paciente.id;
                    document.querySelector("#id-odon").value = info[liParent.dataset.turno - 1].odontologo.id;
                    
                    let formModificar = document.querySelector("#modifTurno");
                    formModificar.addEventListener('submit', (e) => {
                        e.preventDefault();
    
                        let fechaHora = document.querySelector("#fecha").value + " " + document.querySelector("#hora").value;
                        
                        let datosTurno = {
                            id : info[liParent.dataset.turno - 1].id,
                            fechaHora : fechaHora,
                            odontologo : {
                                id: document.querySelector("#id-odon").value
                            },
                            paciente : {
                                id: document.querySelector("#id-pac").value
                            } 
                        }
                
                        let config = {
                            method : "PUT",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body : JSON.stringify(datosTurno)
                        }
                        
                        fetch(`http://localhost:8080/turnos/modificar`, config) 
                        .then((respuesta) => respuesta.json())
                        .then((info) => {
                            listadoTurnos.innerHTML = "";
                            sectionRegistro.innerHTML = "";
                            resultado.innerHTML = `<div class="exito">
                                                        <h4>Guardado con exito!</h4>
                                                        <p>Fecha: ${info.fechaHora.slice(0, 10).split('-').reverse().join('-')} Horario: ${info.fechaHora.slice(11, 16)}hs</p>
                                                    </div>`
                            formModificar.reset();
                        })
                        .catch((error) => console.log(error))
                        
                    })
                    
                })

            })
            

            //borrar el turno buscado
            let botonesBorrar = document.querySelectorAll(".eliminar");
            botonesBorrar.forEach((boton) => {
                boton.addEventListener('click', (e) => {
                    let liParent = boton.parentNode;
                    let config = {
                        method : "DELETE",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body : ""
                    }
    
                    fetch(`http://localhost:8080/turnos/borrar/${liParent.dataset.turno}`, config)
                    .then((respuesta) => {
                        if(respuesta.status === 204) {
                            resultado.innerHTML = `<div class="exito">
                                                        <h4>Eliminado con exito!</h4>
                                                    <div>`;
                            listadoTurnos.innerHTML = "";
                        }
                    })
                    .then((info) => console.log(info))
                    .catch((error) => console.log(error))
                })
    

            })
            
            
           
        })
        .catch((error) => console.log(error))
        
    })

})