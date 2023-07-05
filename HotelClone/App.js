import 'react-native-gesture-handler';
import React from 'react';
import { Text } from 'react-native';
import RootNavigator from './src/navigation/RootNavigator';
import {TailwindProvider} from 'tailwind-rn';
import utilities from './tailwind.json';
import store from './src/Store/store';
import { Provider } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';

const App = () => {
  return (
    <TailwindProvider utilities={utilities}>     
      <Provider store={store}>
        <RootNavigator></RootNavigator>
      </Provider>
    </TailwindProvider>
  );
};

export default App;
