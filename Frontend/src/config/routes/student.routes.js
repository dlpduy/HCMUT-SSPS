const studentRouter ={ 
    path: "student",
    element: null,
    children: [
      {
        subPath: "main",
        Component: null,
      },
      {
        subPath: "*", 
        Component: null,
      },
    ]}
  
  
;
export default studentRouter;