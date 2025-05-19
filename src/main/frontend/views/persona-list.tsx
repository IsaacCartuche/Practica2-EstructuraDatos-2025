import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, Item, PasswordField, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { CuentaService, PersonaCuentaService, PersonaService, TaskService } from 'Frontend/generated/endpoints';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';

import Persona from 'Frontend/generated/com/unl/music/base/models/Persona';
import Cuenta from 'Frontend/generated/com/unl/music/base/models/Cuenta';

import { color } from '@vaadin/vaadin-lumo-styles/color';
import { _items, defaultValidity } from '@vaadin/hilla-lit-form';

export const config: ViewConfig = {
  title: 'Persona',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Persona',
  },
};

type PersonaEntryFormProps = {
  onPersonaCreated?: () => void;
  onCuentaCreated?: () => void;
};


type PersonaEntryFormUpdateProps = {
  onPersonaUpdate?: () => void;
  onCuentaUpdate?: () => void;
};



//GUARDAR Persona
function PersonaEntryForm(props: PersonaEntryFormProps) {
  // constantes para crear a la persona:
  const nombre = useSignal(''); // String
  const edad = useSignal(''); // Integer

  // constantes para crear a la cuenta:
  const email = useSignal(''); // String
  const clave = useSignal(''); // String
  const estado = useSignal(''); // Boolean
  const longitud = useSignal('');

  PersonaService.getLength()
    .then((resultado) => {
      // Aquí 'resultado' ya es el string que devuelve la promesa
        longitud.value = resultado?.toString();
      //console.log(longitud.value); // o úsalo como necesites
    })
    .catch((error) => {
      console.error("Error al obtener la longitud:", error);
    });


  const createPersona = async () => {
    try {

      // console.log("el valor del nombre es: " + nombre);
      // console.log("el valor de la edad es: " + edad);

      // console.log("el valor del email es: " + email);
      // console.log("el valor de la clave es: " + clave);
      // console.log("el valor del estado es: " + estado);
      // console.log("la longitud es: " + longitud.value);

      // evaluacion de que los valores no estén vacios (se hace la validacion de igual forma ya que todos ingresan como String)
      if (nombre.value.trim().length > 0 && edad.value.trim().length > 0 && email.value.trim().length > 0 && clave.value.trim().length > 0 && edad.value.trim().length > 0) {

        if (!await CuentaService.validateCuenta(email.toString())) {
          await PersonaService.createPersona(nombre.value, Number(edad.value));
          await CuentaService.createCuenta(email.value, clave.value, true, parseInt(longitud.value) + 1);

          if (props.onPersonaCreated && props.onCuentaCreated) {
            props.onPersonaCreated();

            props.onCuentaCreated();
          }
          //retorno de valores de persona a vacios
          nombre.value = '';
          edad.value = '';

          //retorno de valores de cuenta a vacios
          email.value = '';
          clave.value = '';
          estado.value = '';
          longitud.value = '';

          dialogOpened.value = false;
          Notification.show('Persona creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo crear, datos erroneos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };


  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Registrar Persona"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createPersona} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="usuario"
            placeholder="Ingrese El usuario"
            aria-label="usuario"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <TextField label="edad"
            placeholder="Ingrese su edad"
            aria-label="edad"
            value={edad.value}
            onValueChanged={(evt) => (edad.value = evt.detail.value)}
          />
          <TextField label="email"
            placeholder="Ingrese su correo"
            aria-label="email"
            value={email.value}
            onValueChanged={(evt) => (email.value = evt.detail.value)}
          />
          <TextField label="clave"
            placeholder="Ingrese su clave"
            aria-label="clave"
            value={clave.value}
            onValueChanged={(evt) => (clave.value = evt.detail.value)}
          />

        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Agregar
      </Button>
    </>
  );
}

// *******************************************************************************************************************************
// UPDATE Persona

function PersonaEntryFormUpdate(props: PersonaEntryFormUpdateProps) {
  //valores de persona
  const usuario = useSignal(props.arguments.usuario);
  const edad = useSignal(props.arguments.edad);
  const ident = useSignal(props.arguments.id);

  //valores de cuenta
  const email = useSignal(props.arguments.email);
  const clave = useSignal(props.arguments.clave);
  const estado = useSignal(props.arguments.estado);

  const updatePersona = async () => {
    try {
      if (usuario.value.trim().length > 0 && edad.value.toString().length > 0 && email.value.toString().length > 0 && clave.value.toString().length > 0 && estado.value.toString().length > 0) {

        await PersonaService.updatePersona(parseInt(ident.value), usuario.value, edad.value);
        console.log(estado.value);
        await CuentaService.updateCuenta(parseInt(ident.value), email.value, clave.value, estado.value, parseInt(ident.value));

        if (props.onPersonaUpdate && props.onCuentaUpdate) {
          props.onPersonaUpdate();
          props.onCuentaUpdate();
        }

        usuario.value = '';
        edad.value = '';
        ident.value = '';

        email.value = '';
        clave.value = '';
        estado.value = '';

        dialogOpened.value = false;
        Notification.show('Persona creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };


  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        aria-label="Actualizar Persona"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={updatePersona} theme="primary">
              Actualizar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="usuario"
            placeholder="Ingrese El usuario"
            aria-label="usuario"
            value={usuario.value}
            onValueChanged={(evt) => (usuario.value = evt.detail.value)}
          />
          <TextField label="edad"
            placeholder="Ingrese su edad"
            aria-label="edad"
            value={edad.value}
            onValueChanged={(evt) => (edad.value = evt.detail.value)}
          />
          <TextField readonly label="email"
            placeholder="Ingrese su correo"
            aria-label="email"
            value={email.value}


          />
          <PasswordField label="clave"
            placeholder="Ingrese su clave"
            aria-label="clave"
            value={clave.value}
            onValueChanged={(evt) => (clave.value = evt.detail.value)}
          />
          <ComboBox label="estado"
            items={['true', 'false']}
            placeholder="Ingrese su estado"
            aria-label="estado"
            value={estado.value}

            onValueChanged={(evt) => (estado.value = evt.detail.value)}

          />
        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Editar
      </Button>
    </>
  );
}





//LISTA DE Persona
export default function PersonaView() {

  const dataProvider = useDataProvider({
    list: () => PersonaCuentaService.listAll(),
  });

  function indexLink({ item }: { item: Cuenta }) {
    return (
      <span>
        <PersonaEntryFormUpdate arguments={item} onPersonaUpdate={dataProvider.refresh} onCuentaUpdate={dataProvider.refresh} />
      </span>
    );
  }

  function indexIndex({ model }: { model: GridItemModel<Cuenta> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  function validation({ item }: { item: Cuenta }) {

    

    if(item.estado.toString() == "true"){
      const color = 'green';
      const label = 'Activo';
      return (
      <span style={{ color: color, fontWeight: 'bold' }}>
        {label}
      </span>
    );
    }else{
      const color = 'red';
      const label = 'inactivo';
      return (
      <span style={{ color: color, fontWeight: 'bold' }}>
        {label}
      </span>
    );
    }


    

  }


  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Persona">
        <Group>
          <PersonaEntryForm onPersonaCreated={dataProvider.refresh} onCuentaCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridColumn path="usuario" header="Usuario" />
        <GridColumn path="edad" header="Edad" />
        <GridColumn path="email" header="Email" />
        <GridColumn renderer={validation} header="Estado" />

        <GridColumn header="Acciones" renderer={indexLink} />
      </Grid>
    </main>
  );
}
