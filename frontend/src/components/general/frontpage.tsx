// import React from "react";
import "./frontpage.css"

import {Link} from "react-router-dom";

export function FrontPage() {
  return (
    <div>
      <h1>What is this page?</h1>
      <p>
        I am so sorry that this looks terrible. Does this page even need to exist?
        Is it possibly a welcome page, kind of showcasing our website? A nice to
        have?
      </p>
      <ul>
        <li><Link to={"/user/chef/profile"}>Chef profile page</Link></li>
        <li>Chef recipes</li>
        <li>Chef active listings</li>
        <li>History</li>
      </ul>
    </div>

  )
}