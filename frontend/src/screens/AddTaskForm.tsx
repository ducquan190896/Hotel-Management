import { Alert,  Button,  Keyboard, KeyboardAvoidingView, KeyboardAvoidingViewBase, StyleSheet, Text, TextInput, TouchableOpacity, TouchableWithoutFeedback, View, Switch } from 'react-native'
import React, { useEffect, useState, useCallback } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import useTailwind from 'tailwind-rn/dist/use-tailwind'
import axios from 'axios'
import { HOST_URL, RootState } from  "../Store/store"
import { login, ResetUser } from '../Store/actions/userAction'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigation } from '@react-navigation/native'
import { NativeStackNavigationProp } from '@react-navigation/native-stack'
import { MainStackParamList } from '../navigation/MainStack'
import AsyncStorage from '@react-native-async-storage/async-storage';
import { DrawerStackParamList } from '../navigation/Drawer'
import { getDepartmentsAction } from '../Store/actions/DepartmentAction'
import {Picker} from '@react-native-picker/picker';
import { DEPARTMENT } from '../models/index.d'

const AddTaskForm = () => {
    const [urgent, setUrgent] = useState<boolean>(false)
    const [name, setName] = useState<string>("")
    const [location, setLocation] = useState<string>("")
    const [description, setDescription] = useState<string>("")
    const [department, setDepartment] = useState<String>("");
    const tw = useTailwind()
    const text: string = "hello"
    const {users, authUser, authError, authSuccess} = useSelector((state: RootState) => state.USERS)
    const dispatch = useDispatch()
    const navigation = useNavigation<NativeStackNavigationProp<DrawerStackParamList>>()
    const {departments, departmentError, departmentSuccess} = useSelector((state: RootState) => state.DEPARTMENTS);
    
    const loadDepartments = useCallback(async () => {
        await  dispatch(getDepartmentsAction() as any);
    }, [])
    
    useEffect(() => {   
      loadDepartments()
    }, [])

    const submitFunction = async () => {
        console.log("login")
        if(name && name.length > 0 && description && description.length > 0 && location && location.length > 0 && department && department.length > 0) {
            const task = {
                name,
                location,
                department,
                description,
                urgent
            }
            try {
                const token = await AsyncStorage.getItem("token")
                if(token == null) {
                    Alert.alert("token null");
                } else {
                    const res = await axios.post(HOST_URL + `/api/tasks/task`, task, {
                        headers: {
                            "Authorization": token ?? ""
                        }
                    })
                    const data = await res.data
                    console.log("add task successfully");
                    console.log(data);
                    setName("")
                    setDepartment("")   
                    setLocation("")
                    setUrgent(false)
                    setDescription("")
                    navigation.navigate('Home')
                }
            } catch (err) {
                Alert.alert("add task failed")
            }
        } else {
            Alert.alert("please fill all required information")
        }
        
    }


  return (

    <KeyboardAvoidingView style={tw('flex-1 bg-white')}>
        <TouchableWithoutFeedback style={tw('flex-1')} onPress={Keyboard.dismiss}>
            <SafeAreaView style={tw('flex-1 items-center justify-center px-4')}>                 
                <TextInput value={name} placeholder="task" onChangeText={(text: string) => setName(text)} style={tw('w-full border border-gray-400 py-2 px-4 rounded-lg text-lg mb-6')}></TextInput>
                <TextInput value={location} placeholder="room" onChangeText={(text: string) => setLocation(text)} style={tw('w-full border border-gray-400 py-2 px-4 rounded-lg text-lg mb-6')}></TextInput>
                <TextInput value={description} placeholder="description" onChangeText={(text: string) => setDescription(text)} style={tw('w-full border border-gray-400 py-2 px-4 rounded-lg text-lg mb-6')}></TextInput>
                <View style={[tw('w-full bg-white rounded-md border border-gray-400 text-zinc-700 font-bold text-lg bg-gray-200 mb-4'), {zIndex: 10, padding: 1}]}>
                    <Picker
                      selectedValue={department}
                      onValueChange={(itemValue, itemIndex) => {
                          setDepartment(itemValue)
                          console.log(itemValue)
                      }}
                      dropdownIconColor="white"
                      mode={Picker.MODE_DROPDOWN}
                      style={tw('bg-white border p-2 border-gray-400 w-full')}
                    >
                     
                     
                      {departments && departments.length > 0 && departments.map((d : DEPARTMENT) =>  <Picker.Item key={d.id} style={tw('bg-white p-2 text-lg border border-gray-400 w-full')} label={d.name} value={d.name}></Picker.Item> )}
                    </Picker>
                </View>
                <View style={tw('flex flex-row items-center w-full my-2 ml-2 justify-start')}>
                    <Text style={tw('text-lg  text-black mr-4')}>Urgent task</Text>
                
                    <Switch
                        trackColor={{false: '#767577', true: '#81b0ff'}}
                        thumbColor={urgent ? 'red' : '#f4f3f4'}
                        ios_backgroundColor="#3e3e3e"
                        onValueChange={() => setUrgent(!urgent)}
                        value={urgent}
                    />
                </View>
                <View style={tw('mt-6')}>
                  <Button  color="#6203fc" title='Add Task'  onPress={submitFunction}  ></Button>
                </View>
            </SafeAreaView>
        </TouchableWithoutFeedback>
    </KeyboardAvoidingView>

  )
}

export default AddTaskForm

const styles = StyleSheet.create({})