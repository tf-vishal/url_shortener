import { useState /*,useEffect*/} from 'react';
import './App.css';
import BackgroundAnimate from './BackgroundAnimate';
import InputShortener from './InputShortener';
import LinkResult from './LinkResult';

function App() {
  const [urlToShorten, setInputValue] = useState("");


  // useEffect(() => {
  //   fetch('http://localhost:8080/inputvalue') // Adjust the port to match your backend
  //     .then(response => response.text())
  //     .then(data => setInputValue(data))
  //     .catch(error => console.error('There was an error!', error));
  // }, []);

  return (
    <div className="container">
      <InputShortener setInputValue={setInputValue} />
      <BackgroundAnimate />
      <LinkResult inputValue={urlToShorten} />
    </div>
  );
}

export default App;