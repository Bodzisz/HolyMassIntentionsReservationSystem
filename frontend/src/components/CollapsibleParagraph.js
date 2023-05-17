import React, { useState } from 'react';

const CollapsibleParagraph = ({ title, children }) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleExpansion = () => {
    setIsExpanded(!isExpanded);
  };

  return (
    <div className={`collapsible-paragraph ${isExpanded ? 'expanded' : ''}`}>
      <div className="collapsible-header" onClick={toggleExpansion}>
        <h3>{title}</h3>
        <span>{isExpanded ? '▲' : '▼'}</span>
      </div>
      {isExpanded && <div className="collapsible-content">{children}</div>}
    </div>
  );
};

export default CollapsibleParagraph;