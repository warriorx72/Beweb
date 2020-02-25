$(document).ready(function() {
    $('#email_envio').on('input', function(evt) {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($('#email_envio').val())) {
            $('#boton_bloqueable').prop("disabled", false);
        } else {
            $('#boton_bloqueable').prop("disabled", true);
        }

    })

    $('#boton_modal').hide();


    //Funcion para Buscar
    $("#BuscarTabla").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        RehacerPaginar(value);
        $('#myTable tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    //Funcion para Filtrar por tipo
    $('#selectCategory').on('change', function() {
        $("#BuscarTabla").val(null);
        switch (this.value) {
            case "Todos":
                if (PrimerCambio == true) {
                    //Esto se hace ya que se ha hecho un filtrado antes, si no se repettiria todo xd 
                    $('#myTable').append(Examenes);
                    $('#myTable').append(Cultivos);
                    $('#myTable').append(Gabinete);
                    $('#myTable').append(Paquete);
                    $('#myTable').append(Perfiles);
                }

                break;
            case "Examenes":
                $('#myTable').append(Examenes);
                $('.TIPO_Perfiles').remove();
                $('.TIPO_Cultivos').remove();
                $('.TIPO_Gabinete').remove();
                $('.TIPO_Paquetes').remove();
                break;
            case "Cultivos":
                $('#myTable').append(Cultivos);
                $('.TIPO_Perfiles').remove();
                $('.TIPO_Examenes').remove();
                $('.TIPO_Gabinete').remove();
                $('.TIPO_Paquetes').remove();
                break;
            case "Gabinete":
                $('#myTable').append(Gabinete);
                $('.TIPO_Perfiles').remove();
                $('.TIPO_Examenes').remove();
                $('.TIPO_Cultivos').remove();
                $('.TIPO_Paquetes').remove();
                break;
            case "Paquetes":
                $('#myTable').append(Paquete);
                $('.TIPO_Perfiles').remove();
                $('.TIPO_Examenes').remove();
                $('.TIPO_Cultivos').remove();
                $('.TIPO_Gabinete').remove();
                break;
            case "Perfiles":
                $('#myTable').append(Perfiles);
                $('.TIPO_Examenes').remove();
                $('.TIPO_Cultivos').remove();
                $('.TIPO_Gabinete').remove();
                $('.TIPO_Paquetes').remove();
                break;
        }

        PrimerCambio = true;
        getPagination('#myTable');
    });



    function RehacerPaginar(val) {
        if (val == "") {
            getPagination('#myTable');
        }
    }

    /*
     * 
     * Coso del Pagination
     * 
     * 
     */
    getPagination('#myTable');
    //getPagination('.table-class');
    //getPagination('table');

    /*					PAGINATION 
    - on change max rows select options fade out all rows gt option value mx = 5
    - append pagination list as per numbers of rows / max rows option (20row/5= 4pages )
    - each pagination li on click -> fade out all tr gt max rows * li num and (5*pagenum 2 = 10 rows)
    - fade out all tr lt max rows * li num - max rows ((5*pagenum 2 = 10) - 5)
    - fade in all tr between (maxRows*PageNum) and (maxRows*pageNum)- MaxRows 
    */


    function getPagination(table) {
        var lastPage = 1;

        $('#maxRows')
            .on('change', function(evt) {
                //$('.paginationprev').html('');						// reset pagination

                lastPage = 1;
                $('.pagination')
                    .find('li')
                    .slice(1, -1)
                    .remove();
                var trnum = 0; // reset tr counter
                var maxRows = parseInt($(this).val()); // get Max Rows from select option

                if (maxRows == 5000) {
                    $('.pagination').hide();
                } else {
                    $('.pagination').show();
                }

                var totalRows = $(table + ' tbody tr').length; // numbers of rows
                $(table + ' tr:gt(0)').each(function() {
                    // each TR in  table and not the header
                    trnum++; // Start Counter
                    if (trnum > maxRows) {
                        // if tr number gt maxRows

                        $(this).hide(); // fade it out
                    }
                    if (trnum <= maxRows) {
                        $(this).show();
                    } // else fade in Important in case if it ..
                }); //  was fade out to fade it in
                if (totalRows > maxRows) {
                    // if tr total rows gt max rows option
                    var pagenum = Math.ceil(totalRows / maxRows); // ceil total(rows/maxrows) to get ..
                    //	numbers of pages
                    for (var i = 1; i <= pagenum;) {
                        // for each page append pagination li
                        $('.pagination #prev')
                            .before(
                                '<li data-page="' +
                                i +
                                '">\
					  <span class="page-link">' +
                                i++ +
                                '<span class="sr-only">(current)</span></span>\
					</li>'
                            )
                            .show();
                    } // end for i
                } // end if row count > max rows
                $('.pagination [data-page="1"]').addClass('active'); // add active class to the first li
                $('.pagination li').on('click', function(evt) {
                    // on click each page
                    evt.stopImmediatePropagation();
                    evt.preventDefault();
                    var pageNum = $(this).attr('data-page'); // get it's number

                    var maxRows = parseInt($('#maxRows').val()); // get Max Rows from select option

                    if (pageNum == 'prev') {
                        if (lastPage == 1) {
                            return;
                        }
                        pageNum = --lastPage;
                    }
                    if (pageNum == 'next') {
                        if (lastPage == $('.pagination li').length - 2) {
                            return;
                        }
                        pageNum = ++lastPage;
                    }

                    lastPage = pageNum;
                    var trIndex = 0; // reset tr counter
                    $('.pagination li').removeClass('active'); // remove active class from all li
                    $('.pagination [data-page="' + lastPage + '"]').addClass('active'); // add active class to the clicked
                    // $(this).addClass('active');					// add active class to the clicked
                    limitPagging();
                    $(table + ' tr:gt(0)').each(function() {
                        // each tr in table not the header
                        trIndex++; // tr index counter
                        // if tr index gt maxRows*pageNum or lt maxRows*pageNum-maxRows fade if out
                        if (
                            trIndex > maxRows * pageNum ||
                            trIndex <= maxRows * pageNum - maxRows
                        ) {
                            $(this).hide();
                        } else {
                            $(this).show();
                        } //else fade in
                    }); // end of for each tr in table
                }); // end of on click pagination list
                limitPagging();
            })
            .val(5)
            .change();

        // end of on select change

        // END OF PAGINATION
    }

    function limitPagging() {
        // alert($('.pagination li').length)

        if ($('.pagination li').length > 7) {
            if ($('.pagination li.active').attr('data-page') <= 3) {
                $('.pagination li:gt(5)').hide();
                $('.pagination li:lt(5)').show();
                $('.pagination [data-page="next"]').show();
            }
            if ($('.pagination li.active').attr('data-page') > 3) {
                $('.pagination li:gt(0)').hide();
                $('.pagination [data-page="next"]').show();
                for (let i = (parseInt($('.pagination li.active').attr('data-page')) - 2); i <= (parseInt($('.pagination li.active').attr('data-page')) + 2); i++) {
                    $('.pagination [data-page="' + i + '"]').show();

                }

            }
        }
    }




    /*
     * 
     * Coso del pagination
     * 
     * 
     */

});


function BotonModal() {
    if (lista.length == 0) {
        $('#boton_modal').hide();
    } else {
        $('#boton_modal').show();
    }
}

function GuardarEstudio(id, tip) {
    $("#BuscarTabla").val(null);
    //Se recogen variables en base a su id e inicial
    //var tip = $('#T_' + id).val();
    var nom = $('#N_' + id + '_' + tip).val();
    var pre = $('#P_' + id + '_' + tip).val();
    var txt = $('#I_' + id + '_' + tip).val();
    var info = $('#INFO_' + txt + '_' + tip).val();
    var nColumnas = $("#contar tr").length;
    var FilaReborn = "<tr class='TIPO_" + tip + "' id='" + txt + "_" + id + "'><td>" + nom + "</td><td>" + pre + "</td><td>" + tip + "</td><td><button style='border:none;' onclick=\"MetodoDeAyuda(\'" + tip + "\'" + ", " + id + ");\"><i class='fa fa-plus'></i></button></td></tr>";
    var H1 = "<input type='hidden' id='N_" + id + "_" + tip + "' value='" + nom + "'>";
    var H2 = "<input type='hidden' id='P_" + id + "_" + tip + "' value='" + pre + "'>";
    var H3 = "<input type='hidden' id='I_" + id + "_" + tip + "' value='" + txt + "'>";
    var H4 = "<input type='hidden' id='INFO_" + id + "_" + tip + "' value='" + info + "'>";
    CrearObjetoEstudio("ADD", nom, pre, id, tip, 1, txt, info, FilaReborn, txt, H1, H2, H3, H4);

    //Se oculta el td que ya se selecciono
    $('#' + txt + '_' + id).remove();

    //Y lo pega del otro lado.
    //Este es el que contiene la cantidad
    //$('#TablaEstudios').append('<tr id="' + txt + '_NUEVO_' + id +  '"><td class="col-5">' + nom + '</td><td  class="col-2"> $' + pre + '</td><td class="col-2"><button type="button" onClick="QuitarEstudio(\'' + id + '\')"><i class="fas fa-trash-alt"></i></button></td><td><input type="number" value="1" min="1" id="CANTIDAD_' + txt + '" onchange="CambiarCantidad(\'' + id + '\')"></td></tr>');
    //Este es el que no contiene cantidad
    $('#TablaEstudios').append("<tr id='" + txt + "_NUEVO_" + id + "'><td class='col-5'>" + nom + "</td><td  class='col-2'> $" + pre + "</td><td class='col-2'><button class='btn btn-danger' type='button' style='border:none;' onclick=\"QuitarEstudio(\'" + tip + "\'" + ", " + id + ");\"><i class='fa fa-trash-o'></i></button></td></tr>");
    $("#cambiar").text(nColumnas);
    BotonModal();
}

function MetodoDeAyuda(tip, id) {
    GuardarEstudio(id, tip);
}

function QuitarEstudio(tip, id) {
    $("#BuscarTabla").val(null);
    //Hay que recuperar el list y obtener el tr
    //var jeje = lista.map(function(item) { return item.id; }).indexOf(id + '_' + txt);
    //lista.splice(removeIndex, 1);
    //lista.
    var FilaNew;
    var txtNew;
    var H1;
    var H2;
    var H3;
    var H4;
    var nColumnas = $("#contar tr").length;
    for (var i = 0; i < lista.length; i++) {
        if (lista[i].id_real == id && lista[i].tipo == tip) {

            FilaNew = lista[i].fila;
            txtNew = lista[i].txtNew;
            H1 = lista[i].H1;
            H2 = lista[i].H2;
            H3 = lista[i].H3;
            H4 = lista[i].H4;
            console.log(lista[i]);
        }
    }

    //Pintamos del lado izquierdo
    //$('#myTable').append(FilaNew);
    $('#myTable').append(H1);
    $('#myTable').append(H2);
    $('#myTable').append(H3);
    $('#myTable').append(H4);


    var nom = $('#N_' + id + '_' + tip).val();
    var pre = $('#P_' + id + '_' + tip).val();
    var txt = $('#I_' + id + '_' + tip).val();
    var info = $('#INFO_' + txt + '_' + tip).val();

    //Quitamos del izquierdo
    $('#' + txtNew + '_NUEVO_' + id).remove();
    //Se hace nuevo la filtracion si es que la hay
    var jeje = $('#selectCategory').val();
    $('#selectCategory').change();
    $('#selectCategory').val(jeje.toString());


    CrearObjetoEstudio("REST", nom, pre, id, tip, 1, txt, info, FilaNew, txtNew, H1, H2, H3, H4);
    BotonModal();
    $('#selectCategory').val(jeje.toString());
    $("#cambiar").text(nColumnas-2);
}


function Ayuda() {
    console.log(lista);
}

function CambiarCantidad(id, tip) {
    var nom = $('#N_' + id + '_' + tip).val();
    var pre = $('#P_' + id + '_' + tip).val();
    var txt = $('#I_' + id + '_' + tip).val();
    var info = $('#INFO_' + txt + '_' + tip).val();
    var cantidad = $('#CANTIDAD_' + txt).val();

    CrearObjetoEstudio("ALTER", nom, pre, id, tip, cantidad, txt, info);
    BotonModal();
}


function CrearObjetoEstudio(accion, nom, pre, id, tip, can, txt, info, FilaReborn, txtNew, H1, H2, H3, H4) {
    switch (accion) {
        case "ADD":
            //Llenamos los array con los datos
            ides[id] = tip;
            cantidades[id + "_" + tip] = can;
            //Estos array son para cantidades
            var temp = {
                id: id + '_' + txt,
                precio: pre,
                can: can,
                id_real: id,
                tipo: tip,
                nom: nom,
                info: info,
                fila: FilaReborn,
                txtNew: txtNew,
                H1: H1,
                H2: H2,
                H3: H3,
                H4: H4
            };
            lista.push(temp);

            break;
        case "REST":
            // Se quita un solo estudio al objeto
            delete ides[id];
            delete cantidades[id + "_" + tip];

            //Se borra tambien lo de los precios
            var removeIndex = lista.map(function(item) {
                return item.id;
            }).indexOf(id + '_' + txt);
            lista.splice(removeIndex, 1);

            break;
        case "ALTER":
            // Se suma uno al estudio ya creado

            //Se borra la cantidad incial con todo y key
            var removeIndex = lista.map(function(item) {
                return item.id;
            }).indexOf(id + '_' + txt);
            lista.splice(removeIndex, 1);

            //Se almacena la nueva con la nueva cantidad
            var temp = {
                id: id + '_' + txt,
                precio: pre,
                can: can,
                id_real: id,
                tipo: tip,
                nom: nom,
                info: info,
                fila: FilaReborn,
                txtNew: txtNew,
                H1: H1,
                H2: H2,
                H3: H3,
                H4: H4
            };
            lista.push(temp);

            break;
        default:
            // code block
    }

    //Se calculan los precios
    var sum = 0;
    for (const prop in lista) {
        sum += parseFloat(lista[prop].can) * parseFloat(lista[prop].precio);
    }


    //Ya aqui se pinta el DOM
    //Se pintan en la tabla
    console.log("Asi quedo la lista despues de guardar: ");
    console.log(lista);
    $('#total').text(" ");
    $('#total').text("$" + sum + ".00");
}


function Guardar() {
    //Se recoge paciente
    var paciente_id = $('#paciente_id').val();

    //Se hace el string
    envio = null;
    envio = JSON.stringify(lista);

    //Se calculan los precios
    var monto = 0;
    for (const prop in lista) {
        monto += parseFloat(lista[prop].can) * parseFloat(lista[prop].precio);
    }

    var correo = $('#email_envio').val();


    //Solicitud Ajax
    $.ajax({
        type: "POST",
        url: "/enviar_cotizacion",
        data: {
            data: envio,
            monto: monto,
            correo: correo,
        },
        success: (data) => {
            //$("#error").text(data);
            location.reload();

        },
        error: (e) => {
            location.reload();
        }
    });
    Swal.fire({
    	  position: 'center',
    	  icon: 'success',
    	  title: 'Se ha enviado la cotización',
    	  showConfirmButton: false,
    	  timer: 3000
    	})
    	setTimeout(function(){
    		 location.reload(1);
    		}, 3500);
}
