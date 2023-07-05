
import 'react-native-gesture-handler';
import React from 'react';
import { Text } from 'react-native';
import RootNavigator from './src/navigation/RootNavigator';
import {TailwindProvider} from 'tailwind-rn';
import utilities from './tailwind.json';
import store from './src/Store/store';
import { Provider } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';
import { SocketProvider } from './src/Context/SocketContext';
const App = () => {
  return (
     //@ts-ignore - TailwindProvider is missing a type definition
    <TailwindProvider utilities={utilities}>     
      <Provider store={store}>
        <SocketProvider>
          <RootNavigator></RootNavigator>
        </SocketProvider>
      </Provider>
    </TailwindProvider>
  );
};

export default App;