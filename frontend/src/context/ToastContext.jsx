import { createContext, useContext, useState, useCallback } from 'react'

const ToastContext = createContext(null)

export const useToast = () => {
  const context = useContext(ToastContext)
  if (!context) throw new Error('useToast must be used within ToastProvider')
  return context
}

export const ToastProvider = ({ children }) => {
  const [toasts, setToasts] = useState([])

  const showToast = useCallback((message, type = 'info', duration = 4000) => {
    const id = Date.now() + Math.random()
    setToasts(prev => [...prev, { id, message, type }])
    setTimeout(() => {
      setToasts(prev => prev.filter(t => t.id !== id))
    }, duration)
  }, [])

  const success = useCallback((msg, dur) => showToast(msg, 'success', dur), [showToast])
  const error = useCallback((msg, dur) => showToast(msg, 'error', dur), [showToast])
  const warning = useCallback((msg, dur) => showToast(msg, 'warning', dur), [showToast])
  const info = useCallback((msg, dur) => showToast(msg, 'info', dur), [showToast])

  return (
    <ToastContext.Provider value={{ toasts, showToast, success, error, warning, info }}>
      {children}
      <div className="toast-container" role="region" aria-live="polite">
        {toasts.map(t => (
          <div key={t.id} className={`toast toast-${t.type}`}>
            <span className="toast-icon">
              {t.type === 'success' && '✓'}
              {t.type === 'error' && '✕'}
              {t.type === 'warning' && '⚠'}
              {t.type === 'info' && 'ℹ'}
            </span>
            <span>{t.message}</span>
          </div>
        ))}
      </div>
    </ToastContext.Provider>
  )
}