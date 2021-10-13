window.addEventListener('load', (e) => {
    
    let botonListado = document.querySelector("#listado-odon"),
        botonNuevoOdon = document.querySelector("#nuevo-odon"),
        formBuscar = document.querySelector("#form-buscar"),
        inputMat = document.querySelector("#id-buscar"),
        resultado = document.querySelector("#resultado"),
        listadoOdon = document.querySelector(".listado"),
        sectionRegistro = document.querySelector(".datos");

    //buscar todos los odontologos
    botonListado.addEventListener('click', (e) => {
        
        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoOdon.innerHTML = "";

        fetch(`http://localhost:8080/odontologos`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            
            sectionRegistro.innerHTML = `<h3>Nuestros Odontólogos</h3>
                                        <ul class="odontologo" id="lista-odon"></ul>`;
            let ulOdon = document.querySelector("#lista-odon");
            info.map((odon) => {
                ulOdon.innerHTML += `<li class="item">${odon.nombre} ${odon.apellido}, Mat ${odon.matricula}</li>`
            })
        })
        .catch((error) => console.log(error))
    })


    //registrar nuevo odontologo
    botonNuevoOdon.addEventListener('click', (e) => {
        listadoOdon.innerHTML = "";
        resultado.innerHTML = "";
        sectionRegistro.innerHTML = `<form id="datosOdontologo">
                                        <label>Nombre</label>
                                        <input type="text" id="nombre" />
                                        <label>Apellido</label>
                                        <input type="text" id="apellido" />
                                        <label>Matrícula</label>
                                        <input type="text" id="matricula" />
                                        <button type="submit" class="alterar">Guardar</button>
                                     </form>
                                     `
        
        let nombreOdon = document.querySelector("#nombre"),
            apellidoOdon = document.querySelector("#apellido"),
            matricula = document.querySelector("#matricula"),
            formulario = document.querySelector("#datosOdontologo");
        
        formulario.addEventListener('submit', (e) => {
            e.preventDefault();

            let datosOdontologo = {
                nombre : nombreOdon.value,
                apellido : apellidoOdon.value,
                matricula : matricula.value 
            }
    
            let config = {
                method : "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body : JSON.stringify(datosOdontologo)
            }

            fetch(`http://localhost:8080/odontologos/registro`, config) 
            .then((respuesta) => respuesta.json())
            .then((info) => {
                sectionRegistro.innerHTML = "";
                resultado.innerHTML = `<div class="exito">
                                            <h4>Guardado con exito!</h4>
                                            <p>Odontologo: ${info.nombre} ${info.apellido}</p>
                                            <p>Matricula: ${info.matricula}</p>
                                        </div>`
                formulario.reset();
            })
            .catch((error) => console.log(error))
        })
    })


    //buscar odontologo por matricula --> modificar/eliminar
    formBuscar.addEventListener('submit', (e) => {
        e.preventDefault();

        sectionRegistro.innerHTML = "";
        resultado.innerHTML = "";
        listadoOdon.innerHTML = "";

        fetch(`http://localhost:8080/odontologos/${inputMat.value}`)
        .then((respuesta) => respuesta.json())
        .then((info) => {
            listadoOdon.innerHTML = `<h3>Nuestros Odontólogos</h3>
                                     <ul class="odontologo" id="lista-odon">
                                        <li>${info.nombre} ${info.apellido}, Mat ${info.matricula}</li>
                                     </ul>
                                     <button type="submit" id="idModificar" class="alterar">Modificar</button>
                                     <button type="submit" id="idEliminar" class="alterar">Eliminar</button>
                                     `
            formBuscar.reset();

            //modificar el odontologo buscado
            let botonModificar = document.querySelector("#idModificar");

            botonModificar.addEventListener('click', (e) => {

                let sectionRegistro = document.querySelector(".datos");

                listadoOdon.innerHTML = "";
                sectionRegistro.innerHTML = `<form id="modifOdontologo">
                                                <label>Nombre</label>
                                                <input type="text" id="nombre" />
                                                <label>Apellido</label>
                                                <input type="text" id="apellido" />
                                                <label>Matrícula</label>
                                                <input type="text" id="matricula" />
                                                <button type="submit" class="alterar">Guardar</button>
                                             </form>`
                                             
                document.querySelector("#nombre").value = info.nombre;
                document.querySelector("#apellido").value = info.apellido;
                document.querySelector("#matricula").value = info.matricula;

                let formModificar = document.querySelector("#modifOdontologo");
                formModificar.addEventListener('submit', (e) => {
                    e.preventDefault();

                    let datosOdontologo = {
                        id : info.id,
                        nombre : document.querySelector("#nombre").value,
                        apellido : document.querySelector("#apellido").value,
                        matricula : document.querySelector("#matricula").value 
                    }
            
                    let config = {
                        method : "PUT",
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body : JSON.stringify(datosOdontologo)
                    }

                    fetch(`http://localhost:8080/odontologos/modificar`, config) 
                    .then((respuesta) => respuesta.json())
                    .then((info) => {
                        sectionRegistro.innerHTML = "";
                        listadoOdon.innerHTML = "";
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Guardado con exito!</h4>
                                                    <p>Odontologo: ${info.nombre} ${info.apellido}</p>
                                                    <p>Matricula: ${info.matricula}</p>
                                                </div>`
                        formModificar.reset();
                    })
                    .catch((error) => console.log(error))

                })
            })


            //borrar el odontologo buscado
            let botonBorrar = document.querySelector("#idEliminar");
            botonBorrar.addEventListener('click', (e) => {

                let config = {
                    method : "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body : ""
                }

                fetch(`http://localhost:8080/odontologos/borrar/${info.id}`, config)
                .then((respuesta) => {
                    if(respuesta.status === 204) {
                        resultado.innerHTML = `<div class="exito">
                                                    <h4>Eliminado con exito!</h4>
                                                <div>`
                        listadoOdon.innerHTML = "";
                    }
                })
                .then((info) => console.log(info))
                .catch((error) => console.log(error))

            })


        })
        .catch((error) => console.log(error))

    })












})