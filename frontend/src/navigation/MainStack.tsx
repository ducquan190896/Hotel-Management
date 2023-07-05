import {StyleSheet, Text, View} from 'react-native';
import React from 'react';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

//import app screen here
import Home from '../screens/Home';
import Drawer from './Drawer';
import LoginScreen from '../screens/LoginScreen';

//Stack will receive a MainStackParamList - Type
const Stack = createNativeStackNavigator<MainStackParamList>();
export type MainStackParamList = {
  Drawer: undefined;
  Login: undefined
};
const MainStack = () => {
  return (
    <Stack.Navigator
      initialRouteName='Login'
      screenOptions={{
        headerShown: false,
      }}>
 
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Drawer" component={Drawer} />
    </Stack.Navigator>
  );
};

export default MainStack;

const styles = StyleSheet.create({});
