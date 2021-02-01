import React from 'react';

/**
 * React Error catcher for component errors
 */
class ErrorCatcher extends React.Component {
  state = {
    error: null,
  };

  componentDidCatch(error, errorInfo) {
    this.setState({error: error});
  }

  render() {
    const { error } = this.state;
    if (error) {
      return (
        <div>
          {`Something went wrong ${error}`}
        </div>

      )
    }
    return this.props.children;
  }
}

export default ErrorCatcher;