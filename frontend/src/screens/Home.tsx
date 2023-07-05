import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import NewTask from './NewTask';
import CompletedTask from './CompletedTask';
import CancelledTask from './CancelledTask';
import { DrawerStackParamList } from '../navigation/Drawer';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { MainStackParamList } from '../navigation/MainStack';
import { CompositeNavigationProp, useNavigation } from '@react-navigation/native';

type HomeScreenNavigationProp = CompositeNavigationProp<
NativeStackNavigationProp<DrawerStackParamList, "Home">,
NativeStackNavigationProp<MainStackParamList>
>;

const Tab = createMaterialTopTabNavigator();


const Home = () => {
  const navigation = useNavigation<HomeScreenNavigationProp>();
  return (
    <Tab.Navigator>
      <Tab.Screen name="NewTask" children={() => <NewTask></NewTask>} options={{title: "New"}}/>
      <Tab.Screen name="CompletedTask" children={() => <CompletedTask></CompletedTask>} options={{title: "Completed"}}/>
      <Tab.Screen name="CancelledTask" children={() => <CancelledTask></CancelledTask>}  options={{title: "Cancelled"}}/>
    </Tab.Navigator>
  )
}

export default Home

const styles = StyleSheet.create({})