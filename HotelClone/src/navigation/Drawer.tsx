import React from 'react';
import {createDrawerNavigator} from '@react-navigation/drawer';

//screens import
import Home from '../screens/Home';
import Setting from '../screens/Setting';

export type DrawerStackParamList = {
 
  Home: undefined;
  Settings: undefined;
};
const DrawerStack = createDrawerNavigator<DrawerStackParamList>();
const Drawer = () => {

  return (
    <DrawerStack.Navigator>
      <DrawerStack.Screen name="Home" component={Home} />
      <DrawerStack.Screen name="Settings" component={Setting} />
    </DrawerStack.Navigator>
  );
};

export default Drawer;
