import React, { useCallback, useEffect, useState } from 'react';
import CopyToClipboard from "react-copy-to-clipboard";


const LinkResult = ({ inputValue }) => {
    const [shortenLink, setShortenLink] = useState("");
    const [copied, setCopied] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    const fetchData = useCallback(()=>{
      setLoading(true);
      fetch('http://localhost:8080/shorten', {
        method: 'POST', 
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ urlToShorten: inputValue })
      })
      .then(response => response.text()) 
      .then(data => {
        setShortenLink(data); // This line uses setShortenLink
      })
      .catch(err => {
        setError(err); // Set error state to true
        console.error('Error fetching data:', err.message); // Log more specific error message
      })
      .finally(() => {
      setLoading(false); 
      });
    }, [inputValue])



    useEffect(() => {
      if(inputValue.length) {
        fetchData();
      }
    }, [inputValue,fetchData]); // This effect depends on inputValue and fetchData, and will re-run when either of them changes
    useEffect(() => {
      const timer = setTimeout(() => {
        setCopied(false);
      }, 1000);
  
      return () => clearTimeout(timer);
    }, [copied]);
    console.log("shortenLink:", shortenLink, "error:", error);
    if(loading) {
      return <p className="noData">Loading...</p>
    }
    if(error) {
        return <p className="noData">Something went wrong :(</p>
    }
    return (
      <>
      {shortenLink && (
        <div className="result">
          <p>{shortenLink}</p>
          <CopyToClipboard
            text={shortenLink}
            onCopy={() => setCopied(true)}
          >
            <button className={copied ? "copied" : ""}>Copy to Clipboard</button>
          </CopyToClipboard>
        </div>
      )}
    </>
    );
}

export default LinkResult;