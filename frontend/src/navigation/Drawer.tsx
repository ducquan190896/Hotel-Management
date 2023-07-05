import React, { useCallback, useEffect, useState, useRef, useLayoutEffect, useContext } from 'react'
import {createDrawerNavigator} from '@react-navigation/drawer';
import { Alert, FlatList, Image, Keyboard, KeyboardAvoidingView, ListRenderItem, StyleSheet, Text, TextInput, TouchableOpacity, TouchableWithoutFeedback, View, Modal, ImageBackground } from 'react-native'
//screens import
import Home from '../screens/Home';
import InProgress from '../screens/InProgress';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../Store/store';
import { useTailwind } from 'tailwind-rn';
import { getDepartmentsAction } from '../Store/actions/DepartmentAction';
import socketContext from '../Context/SocketContext';
import AddTaskForm from '../screens/AddTaskForm';

export type DrawerStackParamList = {
  Home: undefined;
  InProgress: undefined;
  TaskForm: undefined
};
const DrawerStack = createDrawerNavigator<DrawerStackParamList>();
const Drawer = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isVisible, setIsVisible] = useState<boolean>(false);
  const dispatch = useDispatch();
  const {departments, departmentError, departmentSuccess} = useSelector((state: RootState) => state.DEPARTMENTS);
  const {authUser} = useSelector((state: RootState) => state.USERS);
  const tw = useTailwind();
  const { loadChatMessages} : any = useContext(socketContext);

  const loadDepartments = useCallback(async () => {
    await  dispatch(getDepartmentsAction() as any);
  }, [])

  useEffect(() => {
    setIsLoading(true)
    loadDepartments().then(() => loadChatMessages()).then(() => setIsLoading(false));
  }, [])


  return (
    <DrawerStack.Navigator>
      <DrawerStack.Screen name="Home" component={Home} options={{title: "Tasks"}}/>
      <DrawerStack.Screen name="InProgress" component={InProgress} options={{title: "In Progress"}}/>
      <DrawerStack.Screen name="TaskForm" component={AddTaskForm} options={{title: "Add Task"}}/>
    </DrawerStack.Navigator>
  );
};

export default Drawer;
